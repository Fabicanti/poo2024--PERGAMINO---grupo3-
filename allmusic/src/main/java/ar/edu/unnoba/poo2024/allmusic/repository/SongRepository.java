package ar.edu.unnoba.poo2024.allmusic.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>  {
    List<Song> findByArtist_id(Long artist_id);
    List<Song> findByGenre(Genre genre);
    List<Song> findByArtist_idAndGenre(Long artist_id, Genre genre);
}
