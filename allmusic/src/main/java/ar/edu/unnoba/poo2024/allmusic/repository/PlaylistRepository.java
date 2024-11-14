package ar.edu.unnoba.poo2024.allmusic.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unnoba.poo2024.allmusic.model.PlayList;

@Repository
public interface PlaylistRepository extends JpaRepository<PlayList, Long> {
}
