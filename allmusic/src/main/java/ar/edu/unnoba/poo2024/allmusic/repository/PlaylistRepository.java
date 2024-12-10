package ar.edu.unnoba.poo2024.allmusic.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistSummaryDTO;
import ar.edu.unnoba.poo2024.allmusic.model.PlayList;

@Repository
public interface PlaylistRepository extends JpaRepository<PlayList, Long> {
    @Query("SELECT new ar.edu.unnoba.poo2024.allmusic.dto.PlaylistSummaryDTO(p.id, p.name, COUNT(s.id)) " +
           "FROM PlayList p " +
           "LEFT JOIN p.songs s " +
           "GROUP BY p.id, p.name")
    List<PlaylistSummaryDTO> findPlaylistsWithSongCount();

    List<PlayList> findByOwner_id(Long owner_id);
}
