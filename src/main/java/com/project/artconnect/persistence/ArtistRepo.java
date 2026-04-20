package com.project.artconnect.persistence;

import com.project.artconnect.model.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public final class ArtistRepo {
    private final EntityManagerFactory entityManagerFactory;

    public ArtistRepo(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Artist a) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(a);   // insert or update
            entityTransaction.commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()){
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public List<Artist> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT a FROM Artist a", Artist.class)
                    .getResultList();
        }
    }

    public Optional<Artist> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Artist.class, id));
        }
    }

    public Optional<Artist> findByEmail(String email) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT a FROM Artist a WHERE a.contactEmail = :email", Artist.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();
        }
    }

    public List<Artist> findByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT a FROM Artist a WHERE a.name LIKE :name", Artist.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }
}

