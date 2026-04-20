package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * JDBC implementation for ArtistDao.
 * TODO: Students must implement this using JDBC and SQL.
 */
public class JdbcArtistDao implements ArtistDao {

    private static final String SELECT_ARTISTS_SQL = """
            SELECT id, name, bio, birth_year, contact_email, phone, city, website, social_media, is_active
            FROM artists
            """;

    @Override
    public List<Artist> findAll() {
        return executeRead(em -> {
            List<Object[]> rows = em.createNativeQuery(SELECT_ARTISTS_SQL + " ORDER BY name").getResultList();
            List<Artist> artists = new ArrayList<>(rows.size());
            for (Object[] row : rows) {
                artists.add(mapArtist(row));
            }
            return artists;
        });
    }

    @Override
    public void save(Artist artist) {
        executeInTransaction(em -> {
            Query query = em.createNativeQuery("""
                    INSERT INTO artists (name, bio, birth_year, contact_email, phone, city, website, social_media, is_active)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """);
            bindArtistParameters(query, artist);
            query.executeUpdate();
        });
    }

    @Override
    public void update(Artist artist) {
        executeInTransaction(em -> {
            Query query = em.createNativeQuery("""
                    UPDATE artists
                    SET bio = ?, birth_year = ?, contact_email = ?, phone = ?, city = ?, website = ?, social_media = ?, is_active = ?
                    WHERE name = ?
                    """);
            query.setParameter(1, artist.getBio());
            query.setParameter(2, artist.getBirthYear());
            query.setParameter(3, artist.getContactEmail());
            query.setParameter(4, artist.getPhone());
            query.setParameter(5, artist.getCity());
            query.setParameter(6, artist.getWebsite());
            query.setParameter(7, artist.getSocialMedia());
            query.setParameter(8, artist.isActive());
            query.setParameter(9, artist.getName());
            query.executeUpdate();
        });
    }

    @Override
    public void delete(String artistName) {
        executeInTransaction(em -> em.createNativeQuery("DELETE FROM artists WHERE name = ?")
                .setParameter(1, artistName)
                .executeUpdate());
    }

    @Override
    public List<Artist> findByCity(String city) {
        return executeRead(em -> {
            List<Object[]> rows = em.createNativeQuery(SELECT_ARTISTS_SQL + " WHERE city = ? ORDER BY name")
                    .setParameter(1, city)
                    .getResultList();
            List<Artist> artists = new ArrayList<>(rows.size());
            for (Object[] row : rows) {
                artists.add(mapArtist(row));
            }
            return artists;
        });
    }

    private Artist mapArtist(Object[] row) {
        Artist artist = new Artist();
        artist.setId(toLong(row[0]));
        artist.setName((String) row[1]);
        artist.setBio((String) row[2]);
        artist.setBirthYear(toInteger(row[3]));
        artist.setContactEmail((String) row[4]);
        artist.setPhone((String) row[5]);
        artist.setCity((String) row[6]);
        artist.setWebsite((String) row[7]);
        artist.setSocialMedia((String) row[8]);
        artist.setActive(row[9] == null || (Boolean) row[9]);
        return artist;
    }

    private void bindArtistParameters(Query query, Artist artist) {
        query.setParameter(1, artist.getName());
        query.setParameter(2, artist.getBio());
        query.setParameter(3, artist.getBirthYear());
        query.setParameter(4, artist.getContactEmail());
        query.setParameter(5, artist.getPhone());
        query.setParameter(6, artist.getCity());
        query.setParameter(7, artist.getWebsite());
        query.setParameter(8, artist.getSocialMedia());
        query.setParameter(9, artist.isActive());
    }

    private Integer toInteger(Object value) {
        return value == null ? null : ((Number) value).intValue();
    }

    private Long toLong(Object value) {
        return value == null ? null : ((Number) value).longValue();
    }

    private <T> T executeRead(Function<EntityManager, T> action) {
        EntityManager em = JPA.em();
        try {
            return action.apply(em);
        } finally {
            em.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> action) {
        EntityManager em = JPA.em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
