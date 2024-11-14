package ar.edu.unnoba.poo2024.allmusic.resource;

import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.service.SongService;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> getSongs(@RequestHeader("Authorization") String tokenJWT,
                                      @RequestBody Map<String, Object> requestBody) {
        try {
            // Validar el token JWT
            authorizationService.verify(tokenJWT);

            // Obtener todas las instancias de Song
            List<SongResponseDTO> songs = songService.getAllSongs();

            // Aplicar filtro basado en requestBody
            if (requestBody.containsKey("genre")) {
                String genre = (String) requestBody.get("genre");
                songs = songs.stream()
                        .filter(song -> genre.equals(song.getGenre()))
                        .collect(Collectors.toList());
            }

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
