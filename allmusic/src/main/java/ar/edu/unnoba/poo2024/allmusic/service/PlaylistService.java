package ar.edu.unnoba.poo2024.allmusic.service;
import java.util.List;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;

public interface PlaylistService {
    PlaylistResponseDTO createPlaylist(PlaylistRequestDTO playlistRequestDTO);
    PlaylistResponseDTO getPlaylistById(Long playlistId);
    List<PlaylistResponseDTO> getAllPlaylists();
    PlaylistResponseDTO updatePlaylist(Long playlistId, PlaylistRequestDTO playlistRequestDTO);
    void deletePlaylist(Long playlistId);
    void addSongToPlaylist(Long playlistId, Long songId);
}
