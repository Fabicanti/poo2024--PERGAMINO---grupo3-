package ar.edu.unnoba.poo2024.allmusic.resource;

import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.service.SongService;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/song")
public class SongResource {

    @Autowired
    private SongService songService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/songs")
    public ResponseEntity<?> getSongs(@RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Obtener todas las instancias de Song
            List<SongResponseDTO> songs = songService.getAllSongs();

            // Mapear la lista de Song a SongResponseDTO
            List<SongResponseDTO> songResponseDTOs = songs.stream()
                    .map(song -> modelMapper.map(song, SongResponseDTO.class))
                    .collect(Collectors.toList());

            // Retornar la lista de SongResponseDTO con código de estado 200
            return new ResponseEntity<>(songResponseDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSongById(@PathVariable Long id, @RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Obtener la instancia de Song por id
            SongResponseDTO song = songService.getSongById(id).orElseThrow(() -> new RuntimeException("Song not found"));

            // Mapear la instancia de Song a SongResponseDTO
            SongResponseDTO songResponseDTO = modelMapper.map(song, SongResponseDTO.class);

            // Retornar la instancia de SongResponseDTO con código de estado 200
            return new ResponseEntity<>(songResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSong(@RequestBody SongResponseDTO songResponseDTO, @RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Crear una nueva instancia de Song
            SongRequestDTO songRequestDTO = modelMapper.map(songResponseDTO, SongRequestDTO.class);
            SongResponseDTO createdSong = songService.createSong(songRequestDTO);

            // Retornar la instancia de SongResponseDTO con código de estado 201
            return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id, @RequestBody SongResponseDTO songResponseDTO, @RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Actualizar la instancia de Song por id
            SongRequestDTO songRequestDTO = modelMapper.map(songResponseDTO, SongRequestDTO.class);
            SongResponseDTO updatedSong = songService.updateSong(id, songRequestDTO);

            // Retornar la instancia de SongResponseDTO con código de estado 200
            return new ResponseEntity<>(updatedSong, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id, @RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Eliminar la instancia de Song por id
            songService.deleteSong(id);

            // Retornar código de estado 204
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/me-songs")
    public ResponseEntity<?> getMySongs(@RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Obtener todas las instancias de Song
            List<SongResponseDTO> songs = songService.getAllSongs();

            // Mapear la lista de Song a SongResponseDTO
            List<SongResponseDTO> songResponseDTOs = songs.stream()
                    .map(song -> modelMapper.map(song, SongResponseDTO.class))
                    .collect(Collectors.toList());

            // Retornar la lista de SongResponseDTO con código de estado 200
            return new ResponseEntity<>(songResponseDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
