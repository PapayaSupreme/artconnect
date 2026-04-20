package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtworkDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * JDBC implementation for ArtworkDao.
 */
public class JdbcArtworkDao implements ArtworkDao {

    private static final String SELECT_ARTWORKS_SQL = """
            SELECT aw.id, aw.title, aw.creation_year, aw.type, aw.medium, aw.dimensions, aw.description, aw.price, aw.status,
                   a.id, a.name, a.bio, a.birth_year, a.contact_email, a.phone, a.city, a.website, a.social_media, a.is_active
            FROM artworks aw
            JOIN artists a ON a.id = aw.artist_id
            """;

    @Override
    public List<Artwork> findAll() {
        return executeRead(em -> {
            List<Object[]> rows = em.createNativeQuery(SELECT_ARTWORKS_SQL + " ORDER BY aw.title").getResultList();
            List<Artwork> artworks = new ArrayList<>(rows.size());
            for (Object[] row : rows) {
                artworks.add(mapArtwork(row));
            }
            return artworks;
        });
    }

    @Override
    public void save(Artwork artwork) {
        executeInTransaction(em -> {
            Long artistId = findArtistIdByName(em, artwork.getArtist().getName());

            Query query = em.createNativeQuery("""
                    INSERT INTO artworks (title, creation_year, type, medium, dimensions, description, price, status, artist_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """);
            bindArtworkParameters(query, artwork, artistId);
            query.executeUpdate();
        });
    }

    @Override
    public void update(Artwork artwork) {
        executeInTransaction(em -> {
            Long artistId = findArtistIdByName(em, artwork.getArtist().getName());

            Query query = em.createNativeQuery("""
                    UPDATE artworks
                    SET creation_year = ?, type = ?, medium = ?, dimensions = ?, description = ?, price = ?, status = ?, artist_id = ?
                    WHERE title = ?
                    """);
            query.setParameter(1, artwork.getCreationYear());
            query.setParameter(2, artwork.getType());
            query.setParameter(3, artwork.getMedium());
            query.setParameter(4, artwork.getDimensions());
            query.setParameter(5, artwork.getDescription());
            query.setParameter(6, artwork.getPrice());
            query.setParameter(7, normalizeStatus(artwork));
            query.setParameter(8, artistId);
            query.setParameter(9, artwork.getTitle());
            query.executeUpdate();
        });
    }

    @Override
    public void delete(String title) {
        executeInTransaction(em -> em.createNativeQuery("DELETE FROM artworks WHERE title = ?")
                .setParameter(1, title)
                .executeUpdate());
    }

    @Override
    public List<Artwork> findByArtistName(String artistName) {
        return executeRead(em -> {
            List<Object[]> rows = em.createNativeQuery(SELECT_ARTWORKS_SQL + " WHERE a.name = ? ORDER BY aw.title")
                    .setParameter(1, artistName)
                    .getResultList();
            List<Artwork> artworks = new ArrayList<>(rows.size());
            for (Object[] row : rows) {
                artworks.add(mapArtwork(row));
            }
            return artworks;
        });
    }

    private Artwork mapArtwork(Object[] row) {
        Artwork artwork = new Artwork();
        artwork.setId(toLong(row[0]));
        artwork.setTitle((String) row[1]);
        artwork.setCreationYear(toInteger(row[2]));
        artwork.setType((String) row[3]);
        artwork.setMedium((String) row[4]);
        artwork.setDimensions((String) row[5]);
        artwork.setDescription((String) row[6]);
        artwork.setPrice(toDouble(row[7]));
        artwork.setStatus(parseStatus((String) row[8]));
        artwork.setArtist(mapArtist(row, 9));
        return artwork;
    }

    private Artist mapArtist(Object[] row, int base) {
        Artist artist = new Artist();
        artist.setId(toLong(row[base]));
        artist.setName((String) row[base + 1]);
        artist.setBio((String) row[base + 2]);
        artist.setBirthYear(toInteger(row[base + 3]));
        artist.setContactEmail((String) row[base + 4]);
        artist.setPhone((String) row[base + 5]);
        artist.setCity((String) row[base + 6]);
        artist.setWebsite((String) row[base + 7]);
        artist.setSocialMedia((String) row[base + 8]);
        artist.setActive(row[base + 9] == null || (Boolean) row[base + 9]);
        return artist;
    }

    private void bindArtworkParameters(Query query, Artwork artwork, Long artistId) {
        query.setParameter(1, artwork.getTitle());
        query.setParameter(2, artwork.getCreationYear());
        query.setParameter(3, artwork.getType());
        query.setParameter(4, artwork.getMedium());
        query.setParameter(5, artwork.getDimensions());
        query.setParameter(6, artwork.getDescription());
        query.setParameter(7, artwork.getPrice());
        query.setParameter(8, normalizeStatus(artwork));
        query.setParameter(9, artistId);
    }

    private Long findArtistIdByName(EntityManager em, String artistName) {
        List<?> ids = em.createNativeQuery("SELECT id FROM artists WHERE name = ?")
                .setParameter(1, artistName)
                .setMaxResults(1)
                .getResultList();

        if (ids.isEmpty()) {
            throw new IllegalArgumentException("Artist not found: " + artistName);
        }

        return ((Number) ids.get(0)).longValue();
    }

    private Artwork.Status parseStatus(String status) {
        if (status == null) {
            return Artwork.Status.FOR_SALE;
        }
        return Artwork.Status.valueOf(status.toUpperCase());
    }

    private String normalizeStatus(Artwork artwork) {
        Artwork.Status status = artwork.getStatus() == null ? Artwork.Status.FOR_SALE : artwork.getStatus();
        return status.name();
    }

    private Integer toInteger(Object value) {
        return value == null ? null : ((Number) value).intValue();
    }

    private Long toLong(Object value) {
        return value == null ? null : ((Number) value).longValue();
    }

    private double toDouble(Object value) {
        return value == null ? 0 : ((Number) value).doubleValue();
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
