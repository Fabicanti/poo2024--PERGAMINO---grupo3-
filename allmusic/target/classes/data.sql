-- Insertar usuarios
INSERT INTO users (id, username, password, user_type) VALUES
(1, 'artist_user', 'password123', 'Artist'),
(2, 'enthusiast_user', 'password', 'Enthusiast');

-- Insertar en music_artist_users
INSERT INTO music_artist_users (id) VALUES
(1);

-- Insertar en music_enthusiast_users
INSERT INTO music_enthusiast_users (id) VALUES
(2);

-- Insertar canciones
INSERT INTO songs (id, name, genre, artist_id) VALUES
(1, 'Song 1', 'ROCK', 1),
(2, 'Song 2', 'POP', 1),
(3, 'Song 3', 'TECHNO', 1),
(4, 'Song 4', 'JAZZ', 1),
(5, 'Song 5', 'FOLK', 1),
(6, 'Song 6', 'CLASSICAL', 1),
(7, 'Song 7', 'ROCK', 1),
(8, 'Song 8', 'POP', 1),
(9, 'Song 9', 'TECHNO', 1),
(10, 'Song 10', 'JAZZ', 1);

-- Insertar playlists
INSERT INTO playlists (id, name, owner_id) VALUES
(1, 'Playlist 1', 2),
(2, 'Playlist 2', 2);

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