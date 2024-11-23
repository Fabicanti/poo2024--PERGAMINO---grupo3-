CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(31) NOT NULL
);


-- Tabla para MusicEnthusiastUser
CREATE TABLE IF NOT EXISTS music_enthusiast_user (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Tabla para MusicArtistUser
CREATE TABLE IF NOT EXISTS music_artist_user (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Tabla para Song
CREATE TABLE IF NOT EXISTS song (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    genre VARCHAR(31) NOT NULL,
    artist_id BIGINT NOT NULL,
    FOREIGN KEY (artist_id) REFERENCES music_artist_user(id)
);

-- Tabla para PlayList
CREATE TABLE IF NOT EXISTS playlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Tabla intermedia para la relación Many-to-Many entre PlayList y Song
CREATE TABLE IF NOT EXISTS playlist_songs (
    playlist_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    PRIMARY KEY (playlist_id, song_id),
    FOREIGN KEY (playlist_id) REFERENCES playlist(id),
    FOREIGN KEY (song_id) REFERENCES song(id)
);