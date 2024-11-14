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
import ar.edu.unnoba.poo2024.allmusic.model.MusicEnthusiastUser;
import ar.edu.unnoba.poo2024.allmusic.service.AuthenticationService;
import ar.edu.unnoba.poo2024.allmusic.service.UserService;

@RestController
@RequestMapping("/enthusiast")
public class MusicEnthusiastUserResource {

     @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> createEnthusiastUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        try {
            // Convertir CreateUserRequestDTO a MusicEnthusiastUser
            MusicEnthusiastUser musicEnthusiastUser = modelMapper.map(createUserRequestDTO, MusicEnthusiastUser.class);

            // Delegar la creación del usuario a la capa de servicio
            userService.create(musicEnthusiastUser);

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
            MusicEnthusiastUser user = modelMapper.map(authRequest, MusicEnthusiastUser.class);
            String token = authenticationService.authenticate(user);
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
