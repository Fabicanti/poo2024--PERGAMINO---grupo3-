package ar.edu.unnoba.poo2024.allmusic.resource;

import java.util.List;
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

import ar.edu.unnoba.poo2024.allmusic.dto.AddSongDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.CreateAndUpdatePlaylistRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistSummaryDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.service.PlaylistService;
import ar.edu.unnoba.poo2024.allmusic.service.UserService;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistResource {


    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/playlists")
    public ResponseEntity<?> getPlaylists(@RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);
            List<PlaylistSummaryDTO> playlistSummary = playlistService.getPlaylistsWithSongCount();
            return new ResponseEntity<>(playlistSummary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylist(@RequestHeader("Authorization") String tokenJWT,@PathVariable Long id) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);

            PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);
            PlaylistResponseDTO playlistResponseDTO = modelMapper.map(playlist, PlaylistResponseDTO.class);
            return new ResponseEntity<>(playlistResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    

    @PostMapping("/playlist")
    public ResponseEntity<?> createPlaylist(@RequestHeader("Authorization") String tokenJWT, @RequestBody CreateAndUpdatePlaylistRequestDTO createPlaylistRequestDTO) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);

            Long userId = Long.parseLong(jwtTokenUtil.getSubject(token));
            User owner = userService.findById(userId);

            playlistService.createPlaylist(createPlaylistRequestDTO.getName(), owner);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylistName(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id, @RequestBody CreateAndUpdatePlaylistRequestDTO updatePlaylistRequestDTO) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);

            Long userId = Long.parseLong(jwtTokenUtil.getSubject(token));
            User owner = userService.findById(userId);

            playlistService.updatePlaylistName(id, owner, updatePlaylistRequestDTO.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);

            Long userId = Long.parseLong(jwtTokenUtil.getSubject(token));
            User owner = userService.findById(userId);

            playlistService.deletePlaylist(id, owner);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{id}/song")
    public ResponseEntity<?> addSongToPlaylist(@RequestHeader("Authorization") String tokenJWT, @PathVariable Long id, @RequestBody AddSongDTO addSongDTO) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);
            Long userId = Long.parseLong(jwtTokenUtil.getSubject(token));
            User owner = userService.findById(userId);
            playlistService.addSongToPlaylist(id, owner, addSongDTO.getSongID());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/me/playlists")
    public ResponseEntity<?> getMyPlaylists(@RequestHeader("Authorization") String tokenJWT) {
        try {
            String token = tokenJWT.replace("Bearer ", "");
            authorizationService.verify(token);
            Long userId = Long.parseLong(jwtTokenUtil.getSubject(token));
            User owner = userService.findById(userId);

            List<PlaylistResponseDTO> playlistResponseDTO = playlistService.getMyPlaylists(owner);
            return new ResponseEntity<>(playlistResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
