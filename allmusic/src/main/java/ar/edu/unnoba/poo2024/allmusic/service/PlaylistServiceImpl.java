package ar.edu.unnoba.poo2024.allmusic.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistSummaryDTO;
import ar.edu.unnoba.poo2024.allmusic.model.PlayList;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.repository.PlaylistRepository;
import ar.edu.unnoba.poo2024.allmusic.repository.SongRepository;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PlaylistSummaryDTO> getPlaylistsWithSongCount() {
        return playlistRepository.findPlaylistsWithSongCount();
    }

    @Override
    public void createPlaylist(String playlistName, User owner) {
        PlayList playlist = new PlayList();
        playlist.setOwner(owner);
        playlist.setName(playlistName);
        playlistRepository.save(playlist);
    }

    @Override
    public PlaylistResponseDTO getPlaylistById(Long playlistId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    

    @Override
    public void updatePlaylistName(Long playlistId, User owner, String playlistName) {
        PlayList playlistToUpdate = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlistToUpdate.getOwner().getId().equals(owner.getId())) {
            throw new ResourceNotFoundException("No es el dueño de la playlist");
        }
        playlistToUpdate.setName(playlistName);
        playlistRepository.save(playlistToUpdate);
    }

    @Override
    public void deletePlaylist(Long playlistId, User owner) {
        PlayList playlistToDelete = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlistToDelete.getOwner().getId().equals(owner.getId())) {
            throw new ResourceNotFoundException("No es el dueño de la playlist");
        }
        playlistToDelete.getSongs().clear();
        playlistRepository.save(playlistToDelete);

        playlistRepository.deleteById(playlistId);
    }

    @Override
    public void addSongToPlaylist(Long playlistId, User owner, Long songId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(owner.getId())){
            throw new ResourceNotFoundException("No es el dueño de la playlist");}
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
    }

    @Override
    public List<PlaylistResponseDTO> getMyPlaylists(User owner) {
        List<PlayList> playlists = playlistRepository.findByOwner_id(owner.getId());
        return playlists.stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                .collect(Collectors.toList());
    }
}
