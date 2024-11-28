-- Insertar usuarios
INSERT INTO users (username, password, user_type) VALUES
('artist_user', '$2b$10$fYdgiFkOYBtdaYa1ptujt.OXVaDgw9ud4VczrvYTXHKAdY6oeiBx2', 'Artist'), -- password_artist
('enthusiast_user', '$2b$10$EY.gSNDpOPa999CFNSHBwOqiL0k6JcEIJtbhare/IM17y2LfKL6zy', 'Enthusiast'); -- password_enthusiast

-- Insertar en music_artist_users
INSERT INTO music_artist_user (id) VALUES
(1);

-- Insertar en music_enthusiast_users
INSERT INTO music_enthusiast_user (id) VALUES
(2);

-- Insertar canciones
INSERT INTO song (name, genre, artist_id) VALUES
('Song 1', 'ROCK', 1),
('Song 2', 'POP', 1),
('Song 3', 'TECHNO', 1),
('Song 4', 'JAZZ', 1),
('Song 5', 'FOLK', 1),
('Song 6', 'CLASSICAL', 1),
('Song 7', 'ROCK', 1),
('Song 8', 'POP', 1),
('Song 9', 'TECHNO', 1),
('Song 10', 'JAZZ', 1);

-- Insertar playlists
INSERT INTO playlist (name, owner_id) VALUES
('Playlist 1', 2),
('Playlist 2', 2);

-- Insertar canciones en playlists
INSERT INTO playlist_songs (playlist_id, song_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8);