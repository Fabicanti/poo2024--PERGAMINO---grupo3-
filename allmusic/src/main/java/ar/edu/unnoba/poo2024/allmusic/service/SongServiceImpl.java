package ar.edu.unnoba.poo2024.allmusic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ar.edu.unnoba.poo2024.allmusic.dto.SongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.repository.SongRepository;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

@Service
public class SongServiceImpl implements SongService {
    
        private final SongRepository songRepository;
        private final ModelMapper modelMapper;  // Usamos ModelMapper para mapear entre entidad y DTO.
    
        public SongServiceImpl(SongRepository songRepository, ModelMapper modelMapper) {
            this.songRepository = songRepository;
            this.modelMapper = modelMapper;
        }
    
        @Override
        public SongResponseDTO createSong(SongRequestDTO songRequestDTO) {
            Song song = modelMapper.map(songRequestDTO, Song.class);  // Convertimos el DTO a entidad.
            Song savedSong = songRepository.save(song);  // Guardamos la canción en la base de datos.
            return modelMapper.map(savedSong, SongResponseDTO.class);  // Convertimos la entidad guardada a DTO.
        }
    
        @Override
        public Optional<SongResponseDTO> getSongById(Long songId) {
            return songRepository.findById(songId)
                    .map(song -> modelMapper.map(song, SongResponseDTO.class));  // Mapear a DTO si la canción existe.
        }
    
        @Override
        public List<SongResponseDTO> getAllSongs() {
            List<Song> songs = songRepository.findAll();  // Obtenemos todas las canciones.
            return songs.stream()
                    .map(song -> modelMapper.map(song, SongResponseDTO.class))
                    .collect(Collectors.toList());  // Convertimos cada canción a DTO.
        }
    
        @Override
        public SongResponseDTO updateSong(Long songId, SongRequestDTO songRequestDTO) {
            Song songToUpdate = songRepository.findById(songId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada"));  // Lanzar excepción si no existe.
    
            songToUpdate.setName(songRequestDTO.getName());
            songToUpdate.setGenre(songRequestDTO.getGenre());
            songToUpdate.setArtist(songRequestDTO.getArtist());
            
            Song updatedSong = songRepository.save(songToUpdate);  // Guardamos la actualización.
            return modelMapper.map(updatedSong, SongResponseDTO.class);  // Retornamos el DTO actualizado.
        }
    
        @Override
        public void deleteSong(Long songId) {
            if (!songRepository.existsById(songId)) {
                throw new ResourceNotFoundException("Cancion no encontrada");
            }
            songRepository.deleteById(songId);  // Eliminamos la canción por ID.
        }

        
    }