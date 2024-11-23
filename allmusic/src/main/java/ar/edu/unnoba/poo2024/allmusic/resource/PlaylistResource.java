package ar.edu.unnoba.poo2024.allmusic.resource;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.service.PlaylistService;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistResource {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/playlists")
    public ResponseEntity<?> getPlaylists(@RequestHeader("Authorization") String tokenJWT) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Obtener todas las instancias de Playlist
            List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();

            // Mapear la lista de Playlist a PlaylistResponseDTO
            List<PlaylistResponseDTO> playlistResponseDTOs = playlists.stream()
                    .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                    .collect(Collectors.toList());

            // Retornar la lista de PlaylistResponseDTO con código de estado 200
            return new ResponseEntity<>(playlistResponseDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylist(@RequestHeader("Authorization") String tokenJWT,@PathVariable Long id) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Obtener la instancia de Playlist por id
            PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);

            // Mapear la instancia de Playlist a PlaylistResponseDTO
            PlaylistResponseDTO playlistResponseDTO = modelMapper.map(playlist, PlaylistResponseDTO.class);

            // Retornar la instancia de PlaylistResponseDTO con código de estado 200
            return new ResponseEntity<>(playlistResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @PostMapping("/playlist")
    public ResponseEntity<?> createPlaylist(@RequestHeader("Authorization") String tokenJWT, @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Crear una instancia de Playlist
            PlaylistResponseDTO playlist = playlistService.createPlaylist(playlistRequestDTO);

            // Mapear la instancia de Playlist a PlaylistResponseDTO
            PlaylistResponseDTO playlistResponseDTO = modelMapper.map(playlist, PlaylistResponseDTO.class);

            // Retornar la instancia de PlaylistResponseDTO con código de estado 201
            return new ResponseEntity<>(playlistResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id, @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Actualizar la instancia de Playlist por id
            PlaylistResponseDTO playlist = playlistService.updatePlaylist(id, playlistRequestDTO);

            // Mapear la instancia de Playlist a PlaylistResponseDTO
            PlaylistResponseDTO playlistResponseDTO = modelMapper.map(playlist, PlaylistResponseDTO.class);

            // Retornar la instancia de PlaylistResponseDTO con código de estado 200
            return new ResponseEntity<>(playlistResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Eliminar la instancia de Playlist por id
            playlistService.deletePlaylist(id);

            // Retornar código de estado 204
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{id}/song")
    public ResponseEntity<?> addSongToPlaylist(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id, @RequestBody Long songId) {
        try {
            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Agregar una canción a la instancia de Playlist por id
            playlistService.addSongToPlaylist(id, songId);

            // Retornar código de estado 204
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Retornar código de estado 403 si la autorización falla
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /*
     * Get Current User's Playlists
Retorna una lista de las playlists creadas por el usuario actual.
GET /me/playlists
     */
}
