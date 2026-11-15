-- Utilisateur de test (mot de passe: password123 - hashé avec BCrypt)
INSERT INTO users (tracking_id, email, password, first_name, last_name, is_premium, role)
VALUES ('11111111-1111-1111-1111-111111111111', 'test@titan.com', '$2a$10$DowJones...', 'Test', 'User', FALSE, 'USER');

-- Albums
INSERT INTO albums (tracking_id, titre_album, nom_artiste, image_album) VALUES
('a1a1a1a1-1111-1111-1111-111111111111', 'Album 2026', 'Salif Keita', 'https://example.com/album1.jpg'),
('a2a2a2a2-2222-2222-2222-222222222222', 'ARSENAL de Belles', 'Fally Ipupa', 'https://example.com/album2.jpg');

-- Chansons
INSERT INTO songs (tracking_id, titre, audio, artiste, album_tracking_id) VALUES
('s1s1s1s1-1111-1111-1111-111111111111', 'Mandjou', 'https://example.com/mandjou.mp3', 'Salif Keita', 'a1a1a1a1-1111-1111-1111-111111111111'),
('s2s2s2s2-2222-2222-2222-222222222222', 'So Kono', 'https://example.com/sokono.mp3', 'Salif Keita', 'a1a1a1a1-1111-1111-1111-111111111111');