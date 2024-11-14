package ar.edu.unnoba.poo2024.allmusic.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.PlayList;
import ar.edu.unnoba.poo2024.allmusic.repository.PlaylistRepository;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

public class PlaylistServiceImplTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePlaylist() {
        PlaylistRequestDTO requestDTO = new PlaylistRequestDTO("Test Playlist", null);
        PlayList playlist = new PlayList();
        playlist.setName("Test Playlist");

        when(modelMapper.map(requestDTO, PlayList.class)).thenReturn(playlist);
        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(modelMapper.map(playlist, PlaylistResponseDTO.class)).thenReturn(new PlaylistResponseDTO(1L, "Test Playlist", null));

        PlaylistResponseDTO responseDTO = playlistService.createPlaylist(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Test Playlist", responseDTO.getName());
    }

    @Test
    public void testGetPlaylistById() {
        PlayList playlist = new PlayList();
        playlist.setId(1L);
        playlist.setName("Test Playlist");

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(modelMapper.map(playlist, PlaylistResponseDTO.class)).thenReturn(new PlaylistResponseDTO(1L, "Test Playlist", null));

        PlaylistResponseDTO responseDTO = playlistService.getPlaylistById(1L);

        assertNotNull(responseDTO);
        assertEquals("Test Playlist", responseDTO.getName());
    }

    @Test
    public void testGetAllPlaylists() {
        PlayList playlist1 = new PlayList();
        playlist1.setId(1L);
        playlist1.setName("Playlist 1");

        PlayList playlist2 = new PlayList();
        playlist2.setId(2L);
        playlist2.setName("Playlist 2");

        when(playlistRepository.findAll()).thenReturn(Arrays.asList(playlist1, playlist2));
        when(modelMapper.map(playlist1, PlaylistResponseDTO.class)).thenReturn(new PlaylistResponseDTO(1L, "Playlist 1", null));
        when(modelMapper.map(playlist2, PlaylistResponseDTO.class)).thenReturn(new PlaylistResponseDTO(2L, "Playlist 2", null));

        List<PlaylistResponseDTO> responseDTOs = playlistService.getAllPlaylists();

        assertNotNull(responseDTOs);
        assertEquals(2, responseDTOs.size());
    }

    @Test
    public void testUpdatePlaylist() {
        PlaylistRequestDTO requestDTO = new PlaylistRequestDTO("Updated Playlist", null);
        PlayList playlist = new PlayList();
        playlist.setId(1L);
        playlist.setName("Test Playlist");

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(modelMapper.map(playlist, PlaylistResponseDTO.class)).thenReturn(new PlaylistResponseDTO(1L, "Updated Playlist", null));

        PlaylistResponseDTO responseDTO = playlistService.updatePlaylist(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Updated Playlist", responseDTO.getName());
    }

    @Test
    public void testDeletePlaylist() {
        when(playlistRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> playlistService.deletePlaylist(1L));
        verify(playlistRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePlaylistNotFound() {
        when(playlistRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> playlistService.deletePlaylist(1L));
    }
}
