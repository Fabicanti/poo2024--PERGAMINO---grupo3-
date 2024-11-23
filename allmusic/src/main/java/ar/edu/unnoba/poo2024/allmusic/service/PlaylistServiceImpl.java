package ar.edu.unnoba.poo2024.allmusic.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.PlayList;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
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
    public PlaylistResponseDTO createPlaylist(PlaylistRequestDTO playlistRequestDTO) {
        PlayList playlist = modelMapper.map(playlistRequestDTO, PlayList.class);
        PlayList savedPlaylist = playlistRepository.save(playlist);
        return modelMapper.map(savedPlaylist, PlaylistResponseDTO.class);
    }

    @Override
    public PlaylistResponseDTO getPlaylistById(Long playlistId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    @Override
    public List<PlaylistResponseDTO> getAllPlaylists() {
        List<PlayList> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PlaylistResponseDTO updatePlaylist(Long playlistId, PlaylistRequestDTO playlistRequestDTO) {
        PlayList playlistToUpdate = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        playlistToUpdate.setName(playlistRequestDTO.getName());
        playlistToUpdate.setSongs(playlistRequestDTO.getSongs());

        PlayList updatedPlaylist = playlistRepository.save(playlistToUpdate);
        return modelMapper.map(updatedPlaylist, PlaylistResponseDTO.class);
    }

    @Override
    public void deletePlaylist(Long playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException("Playlist not found");
        }
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
    }
}
