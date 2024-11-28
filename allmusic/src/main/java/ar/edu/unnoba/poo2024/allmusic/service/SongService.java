package ar.edu.unnoba.poo2024.allmusic.service;

import java.util.List;
import java.util.Optional;
import ar.edu.unnoba.poo2024.allmusic.dto.SongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;

public interface SongService {
    SongResponseDTO createSong(SongRequestDTO songRequestDTO);
    Optional<SongResponseDTO> getSongById(Long songId);
    List<SongResponseDTO> getAllSongs();
    SongResponseDTO updateSong(Long songId, SongRequestDTO songRequestDTO);
    void deleteSong(Long songId);
    List<SongResponseDTO> getSongsByArtist(String artistName);
    List<SongResponseDTO> getSongsByGenre(String genre);
    List<SongResponseDTO> getSongsByArtistAndGenre(String artistName, String genre);
}
