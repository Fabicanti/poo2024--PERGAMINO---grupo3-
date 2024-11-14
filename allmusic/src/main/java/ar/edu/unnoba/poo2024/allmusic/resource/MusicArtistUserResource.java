package ar.edu.unnoba.poo2024.allmusic.resource;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.service.AuthenticationService;
import ar.edu.unnoba.poo2024.allmusic.service.UserService;

@RestController
@RequestMapping("/artist")
public class MusicArtistUserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> createArtistUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        try {
            // Convertir CreateUserRequestDTO a MusicArtistUser
            MusicArtistUser musicArtistUser = modelMapper.map(createUserRequestDTO, MusicArtistUser.class);

            // Delegar la creación del usuario a la capa de servicio
            userService.create(musicArtistUser);

            // Retornar el código 201 si la creación fue exitosa
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre una excepción, retornar el código 409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDTO authRequest) {
        try {
            MusicArtistUser user = modelMapper.map(authRequest, MusicArtistUser.class);
            String token = authenticationService.authenticate(user);
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
