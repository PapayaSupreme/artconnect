\c artconnect

BEGIN;

-- Reset all data to keep inserts deterministic.
TRUNCATE TABLE
    reviews,
    bookings,
    member_favorite_disciplines,
    exhibition_artworks,
    artwork_tags_map,
    artist_disciplines,
    exhibitions,
    workshops,
    community_members,
    artworks,
    galleries,
    artwork_tags,
    disciplines,
    artists
RESTART IDENTITY CASCADE;

INSERT INTO artists (id, name, bio, birth_year, contact_email, phone, city, website, social_media, is_active) VALUES
    (1, 'Lina Moreau', 'Mixed-media artist focused on urban memory.', 1989, 'lina.moreau@artconnect.org', '+33-1-44-10-2001', 'Paris', 'https://linamoreau.art', '@linamoreau', TRUE),
    (2, 'Diego Salazar', 'Sculptor exploring recycled industrial materials.', 1983, 'diego.salazar@artconnect.org', '+34-91-555-1022', 'Madrid', 'https://diegosalazar.studio', '@diegoscultura', TRUE),
    (3, 'Amira Nasser', 'Digital installation artist blending code and sound.', 1992, 'amira.nasser@artconnect.org', '+20-2-2200-7711', 'Cairo', 'https://amiranasser.io', '@amiracodeart', TRUE),
    (4, 'Noah Kim', 'Photographer documenting coastal communities.', 1990, 'noah.kim@artconnect.org', '+82-2-470-1190', 'Seoul', 'https://noahkim.photo', '@noahkimphoto', TRUE),
    (5, 'Elena Rossi', 'Ceramic and textile artist inspired by botanical forms.', 1987, 'elena.rossi@artconnect.org', '+39-06-8899-3311', 'Rome', 'https://elenarossi.art', '@elenarossiatelier', TRUE);

INSERT INTO disciplines (id, name) VALUES
    (1, 'Painting'),
    (2, 'Sculpture'),
    (3, 'Photography'),
    (4, 'Digital Art'),
    (5, 'Ceramics'),
    (6, 'Textile Art');

INSERT INTO artist_disciplines (artist_id, discipline_id) VALUES
    (1, 1),
    (1, 6),
    (2, 2),
    (2, 5),
    (3, 4),
    (3, 1),
    (4, 3),
    (4, 4),
    (5, 5),
    (5, 6);

INSERT INTO artwork_tags (id, name) VALUES
    (1, 'sustainability'),
    (2, 'identity'),
    (3, 'urban'),
    (4, 'experimental'),
    (5, 'nature'),
    (6, 'light');

INSERT INTO artworks (id, title, creation_year, type, medium, dimensions, description, price, status, artist_id) VALUES
    (1, 'Echoes of Transit', 2023, 'Painting', 'Acrylic on canvas', '120x90 cm', 'Layered city maps and commuter traces.', 2400.00, 'FOR_SALE', 1),
    (2, 'Iron Garden', 2022, 'Sculpture', 'Recycled steel', '210x80x75 cm', 'Large-form sculpture built from salvaged metal.', 6800.00, 'EXHIBITED', 2),
    (3, 'Signal Bloom', 2024, 'Digital Installation', 'LED and generative software', 'Variable', 'Interactive visuals responding to ambient sound.', 9500.00, 'EXHIBITED', 3),
    (4, 'Tidekeepers', 2021, 'Photography', 'Archival pigment print', '70x100 cm', 'Portrait series from fishing villages.', 1500.00, 'FOR_SALE', 4),
    (5, 'Clay Canopy', 2023, 'Ceramic', 'Stoneware and glaze', '60x60x45 cm', 'Clustered ceramic forms inspired by seed pods.', 3200.00, 'FOR_SALE', 5),
    (6, 'Night Window', 2022, 'Painting', 'Oil on linen', '100x100 cm', 'Nocturnal reflections and storefront light.', 2800.00, 'SOLD', 1);

INSERT INTO artwork_tags_map (artwork_id, tag_id) VALUES
    (1, 3),
    (1, 2),
    (2, 1),
    (2, 5),
    (3, 4),
    (3, 6),
    (4, 2),
    (4, 5),
    (5, 5),
    (5, 1),
    (6, 6),
    (6, 3);

INSERT INTO galleries (id, name, address, owner_name, opening_hours, contact_phone, rating, website) VALUES
    (1, 'Northlight Gallery', '15 Rue des Ateliers, Paris', 'Camille Renault', 'Tue-Sun 10:00-19:00', '+33-1-44-10-0101', 4.70, 'https://northlight.gallery'),
    (2, 'Foundry Space', '88 Calle Mayor, Madrid', 'Javier Molina', 'Mon-Sat 11:00-20:00', '+34-91-555-0102', 4.40, 'https://foundryspace.es'),
    (3, 'Delta Contemporary', '7 Corniche Road, Alexandria', 'Mona El-Sayed', 'Wed-Mon 12:00-21:00', '+20-3-480-0103', 4.85, 'https://deltacontemporary.eg'),
    (4, 'Harbor Lens House', '21 Marine Way, Busan', 'Park Ji-ho', 'Daily 09:30-18:30', '+82-51-210-0104', 4.60, 'https://harborlens.kr');

INSERT INTO exhibitions (id, title, start_date, end_date, description, gallery_id, curator_name, theme) VALUES
    (1, 'Cities in Layers', DATE '2025-03-01', DATE '2025-04-20', 'Works exploring memory, migration, and changing streetscapes.', 1, 'Alice Fournier', 'Urban Memory'),
    (2, 'Reclaimed Matter', DATE '2025-05-10', DATE '2025-06-25', 'Sculptures and installations made from recovered materials.', 2, 'Hector Ruiz', 'Sustainability'),
    (3, 'Signal and Shore', DATE '2025-07-05', DATE '2025-08-18', 'Digital and photographic works tied to coastlines and data.', 4, 'Kim Eun-seo', 'Data and Sea'),
    (4, 'Botanical Futures', DATE '2025-09-01', DATE '2025-10-15', 'Ceramics, textiles, and paintings inspired by plant systems.', 3, 'Nadia Helmy', 'Biomorphic Forms');

INSERT INTO exhibition_artworks (exhibition_id, artwork_id) VALUES
    (1, 1),
    (1, 6),
    (2, 2),
    (2, 5),
    (3, 3),
    (3, 4),
    (4, 5),
    (4, 1);

INSERT INTO community_members (id, name, email, birth_year, phone, city, membership_type) VALUES
    (1, 'Sofia Martin', 'sofia.martin@members.artconnect.org', 1996, '+33-6-55-10-1001', 'Paris', 'premium'),
    (2, 'Omar Haddad', 'omar.haddad@members.artconnect.org', 1991, '+20-10-3344-2202', 'Alexandria', 'free'),
    (3, 'Mina Park', 'mina.park@members.artconnect.org', 1998, '+82-10-7711-3303', 'Busan', 'premium'),
    (4, 'Clara Vega', 'clara.vega@members.artconnect.org', 1988, '+34-600-220-4404', 'Madrid', 'free'),
    (5, 'Luca Bianchi', 'luca.bianchi@members.artconnect.org', 1994, '+39-320-7788-5505', 'Rome', 'premium');

INSERT INTO member_favorite_disciplines (member_id, discipline_id) VALUES
    (1, 1),
    (1, 4),
    (2, 2),
    (2, 5),
    (3, 3),
    (3, 4),
    (4, 1),
    (4, 2),
    (5, 5),
    (5, 6);

INSERT INTO workshops (id, title, workshop_date, duration_minutes, max_participants, price, instructor_id, location, description, level) VALUES
    (1, 'Urban Texture Lab', TIMESTAMP '2025-04-12 14:00:00', 180, 16, 45.00, 1, 'Northlight Studio A', 'Mixed-media layering with found paper and paint.', 'beginner'),
    (2, 'Metal Reborn Studio', TIMESTAMP '2025-05-18 10:30:00', 240, 12, 70.00, 2, 'Foundry Workshop Hall', 'Safe cutting and assembling techniques for scrap sculpture.', 'intermediate'),
    (3, 'Generative Light Coding', TIMESTAMP '2025-07-20 16:00:00', 210, 20, 85.00, 3, 'Harbor Lens Media Room', 'Build reactive visuals from live audio streams.', 'advanced'),
    (4, 'Ceramic Seed Forms', TIMESTAMP '2025-09-07 11:00:00', 150, 14, 55.00, 5, 'Delta Clay Lab', 'Hand-building organic modules and glazing basics.', 'beginner');

INSERT INTO bookings (id, workshop_id, member_id, booking_date, payment_status) VALUES
    (1, 1, 1, TIMESTAMP '2025-04-01 09:15:00', 'PAID'),
    (2, 1, 4, TIMESTAMP '2025-04-02 13:40:00', 'PENDING'),
    (3, 2, 2, TIMESTAMP '2025-05-01 10:05:00', 'PAID'),
    (4, 2, 5, TIMESTAMP '2025-05-03 16:25:00', 'CANCELLED'),
    (5, 3, 3, TIMESTAMP '2025-06-20 08:50:00', 'PAID'),
    (6, 3, 1, TIMESTAMP '2025-06-22 12:00:00', 'PENDING'),
    (7, 4, 5, TIMESTAMP '2025-08-25 17:45:00', 'PAID'),
    (8, 4, 2, TIMESTAMP '2025-08-29 11:10:00', 'PAID');

INSERT INTO reviews (id, reviewer_member_id, artwork_id, rating, comment, review_date) VALUES
    (1, 1, 3, 5, 'Immersive and technically brilliant.', DATE '2025-07-22'),
    (2, 2, 2, 4, 'Strong concept and impressive scale.', DATE '2025-06-01'),
    (3, 3, 4, 5, 'Emotionally rich portrait series.', DATE '2025-08-01'),
    (4, 4, 1, 4, 'Loved the layered textures and story.', DATE '2025-04-15'),
    (5, 5, 5, 5, 'Beautiful craftsmanship and glaze work.', DATE '2025-09-20'),
    (6, 2, 6, 3, 'Atmospheric piece, though less striking in person.', DATE '2025-04-18');

COMMIT;

