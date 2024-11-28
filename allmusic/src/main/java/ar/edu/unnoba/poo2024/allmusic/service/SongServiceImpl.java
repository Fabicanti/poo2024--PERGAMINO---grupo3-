package ar.edu.unnoba.poo2024.allmusic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unnoba.poo2024.allmusic.dto.SongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.repository.SongRepository;
import ar.edu.unnoba.poo2024.allmusic.repository.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SongResponseDTO createSong(SongRequestDTO songRequestDTO) {
        try {
            if (songRequestDTO.getArtist() == null || songRequestDTO.getArtist().isEmpty()) {
                throw new IllegalArgumentException("Artist username must not be null or empty");
            }
            MusicArtistUser artist = userRepository.findArtistByUsername(songRequestDTO.getArtist())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            Song song = new Song();
            song.setArtist(artist);
            song.setName(songRequestDTO.getName());
            song.setGenre(Genre.valueOf(songRequestDTO.getGenre().toUpperCase()));
            Song savedSong = songRepository.save(song);
            return modelMapper.map(savedSong, SongResponseDTO.class);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<SongResponseDTO> getSongById(Long songId) {
        return songRepository.findById(songId)
                .map(song -> modelMapper.map(song, SongResponseDTO.class)); // Mapear a DTO si la canción existe.
    }

    @Override
    public List<SongResponseDTO> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SongResponseDTO updateSong(Long songId, SongRequestDTO songRequestDTO) {
        Song songToUpdate = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada"));
        songToUpdate.setName(songRequestDTO.getName());
        songToUpdate.setGenre(Genre.valueOf(songRequestDTO.getGenre().toUpperCase()));
        MusicArtistUser artist = userRepository.findArtistByUsername(songRequestDTO.getArtist())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        songToUpdate.setArtist(artist);
        Song updatedSong = songRepository.save(songToUpdate);
        return modelMapper.map(updatedSong, SongResponseDTO.class);
    }

    @Override
    public void deleteSong(Long songId) {
        if (!songRepository.existsById(songId)) {
            throw new ResourceNotFoundException("Cancion no encontrada");
        }
        songRepository.deleteById(songId);
    }

    @Override
    public List<SongResponseDTO> getSongsByArtist(String artistName) {
        MusicArtistUser artist = userRepository.findArtistByUsername(artistName)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        List<Song> songs = songRepository.findByArtist_id(artist.getId());
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SongResponseDTO> getSongsByGenre(String genre) {
        List<Song> songs = songRepository.findByGenre(Genre.valueOf(genre.toUpperCase()));
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SongResponseDTO> getSongsByArtistAndGenre(String artistName, String genre) {
        MusicArtistUser artist = userRepository.findArtistByUsername(artistName)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        List<Song> songs = songRepository.findByArtist_idAndGenre(artist.getId(), Genre.valueOf(genre.toUpperCase()));
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
    }

}