package ar.edu.unnoba.poo2024.allmusic.service;
import java.util.List;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistSummaryDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;

public interface PlaylistService {
    List<PlaylistSummaryDTO> getPlaylistsWithSongCount();
    void createPlaylist(String playlistName, User owner);
    PlaylistResponseDTO getPlaylistById(Long playlistId);
    void updatePlaylistName(Long playlistId, User owner, String playlistName);
    void deletePlaylist(Long playlistId, User owner);
    void addSongToPlaylist(Long playlistId, User owner, Long songId);
    List<PlaylistResponseDTO> getMyPlaylists(User owner);
}
