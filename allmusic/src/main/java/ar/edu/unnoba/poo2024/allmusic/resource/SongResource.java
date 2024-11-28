package ar.edu.unnoba.poo2024.allmusic.resource;

import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.dto.SongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.service.SongService;
import ar.edu.unnoba.poo2024.allmusic.service.UserService;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/song")
public class SongResource {

    @Autowired
    private SongService songService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/songs")
    public ResponseEntity<?> getSongs(@RequestHeader("Authorization") String tokenJWT,
            @RequestParam(value = "artist", required = false) String artistName,
            @RequestParam(value = "genre", required = false) String genre) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            String userType = jwtTokenUtil.getUserType(token);
            if (!userType.equals("Artist")) {
                return new ResponseEntity<>("Acces forbidden for Artist", HttpStatus.FORBIDDEN);
            }
            authorizationService.verify(token);
            List<SongResponseDTO> songResponseDTO;
            if (artistName != null && genre != null) {
                songResponseDTO = songService.getSongsByArtistAndGenre(artistName, genre);
            } else if (artistName != null) {
                songResponseDTO = songService.getSongsByArtist(artistName);
            } else if (genre != null) {
                songResponseDTO = songService.getSongsByGenre(genre);
            } else {
                songResponseDTO = songService.getAllSongs();
            }
            return new ResponseEntity<>(songResponseDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSongById(@PathVariable Long id, @RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);
            SongResponseDTO songResponseDTO = songService.getSongById(id)
                    .orElseThrow(() -> new RuntimeException("Song not found"));
            return new ResponseEntity<>(songResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSong(@RequestBody SongRequestDTO songRequestDTO,
            @RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            String userType = jwtTokenUtil.getUserType(token);
            if (!userType.equals("Artist")) {
                System.out.println("Access forbidden for non-Artist users");
                return new ResponseEntity<>("Access forbidden for non-Artist users", HttpStatus.FORBIDDEN);
            }
            authorizationService.verify(token);
            SongResponseDTO createdSong = songService.createSong(songRequestDTO);
            return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id, @RequestBody SongRequestDTO songRequestDTO,
            @RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            String userType = jwtTokenUtil.getUserType(token);
            if (!userType.equals("Artist")) {
                System.out.println("Access forbidden for non-Artist users");
                return new ResponseEntity<>("Access forbidden for non-Artist users", HttpStatus.FORBIDDEN);
            }
            authorizationService.verify(token);
            SongResponseDTO updatedSong = songService.updateSong(id, songRequestDTO);
            return new ResponseEntity<>(updatedSong, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id, @RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            String userType = jwtTokenUtil.getUserType(token);

            if (!userType.equals("Artist")) {
                System.out.println("Access forbidden for non-Artist users");
                return new ResponseEntity<>("Access forbidden for non-Artist users", HttpStatus.FORBIDDEN);
            }
            authorizationService.verify(token);
            songService.deleteSong(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/me-songs")
    public ResponseEntity<?> getMySongs(@RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            String userType = jwtTokenUtil.getUserType(token);
            if (!userType.equals("Artist")) {
                return new ResponseEntity<>("Acces forbidden for User", HttpStatus.FORBIDDEN);
            }
            authorizationService.verify(token);
            Long artistId = Long.parseLong(jwtTokenUtil.getSubject(token));
            MusicArtistUser artist = (MusicArtistUser) userService.findById(artistId);

            List<SongResponseDTO> songResponseDTOs = songService.getSongsByArtist(artist.getUsername());
            return new ResponseEntity<>(songResponseDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
