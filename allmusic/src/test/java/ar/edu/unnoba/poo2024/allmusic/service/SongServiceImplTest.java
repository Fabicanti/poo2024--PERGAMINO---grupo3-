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

import ar.edu.unnoba.poo2024.allmusic.dto.SongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.repository.SongRepository;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

public class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SongServiceImpl songService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSong() {
        MusicArtistUser artist = new MusicArtistUser("artist_username", "artist_password", "artist_name");
        Genre genre = Genre.ROCK;
        SongRequestDTO requestDTO = new SongRequestDTO(artist, genre, "Test Song");
        Song song = new Song();
        song.setName("Test Song");
        song.setGenre(genre);
        song.setArtist(artist);

        when(modelMapper.map(requestDTO, Song.class)).thenReturn(song);
        when(songRepository.save(song)).thenReturn(song);
        when(modelMapper.map(song, SongResponseDTO.class)).thenReturn(new SongResponseDTO(1L, "Test Song", genre, artist));

        SongResponseDTO responseDTO = songService.createSong(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Test Song", responseDTO.getName());
    }

    @Test
    public void testGetSongById() {
        MusicArtistUser artist = new MusicArtistUser("artist_username", "artist_password", "artist_name");
        Genre genre = Genre.ROCK;
        Song song = new Song();
        song.setId(1L);
        song.setName("Test Song");
        song.setGenre(genre);
        song.setArtist(artist);

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(modelMapper.map(song, SongResponseDTO.class)).thenReturn(new SongResponseDTO(1L, "Test Song", genre, artist));

        Optional<SongResponseDTO> responseDTO = songService.getSongById(1L);

        assertTrue(responseDTO.isPresent());
        assertEquals("Test Song", responseDTO.get().getName());
    }

    @Test
    public void testGetAllSongs() {
        MusicArtistUser artist1 = new MusicArtistUser("artist1_username", "artist1_password", "artist1_name");
        MusicArtistUser artist2 = new MusicArtistUser("artist2_username", "artist2_password", "artist2_name");
        Genre genre1 = Genre.ROCK;
        Genre genre2 = Genre.POP;

        Song song1 = new Song();
        song1.setId(1L);
        song1.setName("Song 1");
        song1.setGenre(genre1);
        song1.setArtist(artist1);

        Song song2 = new Song();
        song2.setId(2L);
        song2.setName("Song 2");
        song2.setGenre(genre2);
        song2.setArtist(artist2);

        when(songRepository.findAll()).thenReturn(Arrays.asList(song1, song2));
        when(modelMapper.map(song1, SongResponseDTO.class)).thenReturn(new SongResponseDTO(1L, "Song 1", genre1, artist1));
        when(modelMapper.map(song2, SongResponseDTO.class)).thenReturn(new SongResponseDTO(2L, "Song 2", genre2, artist2));

        List<SongResponseDTO> responseDTOs = songService.getAllSongs();

        assertNotNull(responseDTOs);
        assertEquals(2, responseDTOs.size());
    }

    @Test
    public void testUpdateSong() {
        MusicArtistUser artist = new MusicArtistUser("artist_username", "artist_password", "artist_name");
        Genre genre = Genre.ROCK;
        SongRequestDTO requestDTO = new SongRequestDTO(artist, genre, "Updated Song");
        Song song = new Song();
        song.setId(1L);
        song.setName("Test Song");
        song.setGenre(genre);
        song.setArtist(artist);

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(songRepository.save(song)).thenReturn(song);
        when(modelMapper.map(song, SongResponseDTO.class)).thenReturn(new SongResponseDTO(1L, "Updated Song", genre, artist));

        SongResponseDTO responseDTO = songService.updateSong(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Updated Song", responseDTO.getName());
    }

    @Test
    public void testDeleteSong() {
        when(songRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> songService.deleteSong(1L));
        verify(songRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSongNotFound() {
        when(songRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> songService.deleteSong(1L));
    }
}
