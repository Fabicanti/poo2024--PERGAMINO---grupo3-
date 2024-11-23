package ar.edu.unnoba.poo2024.allmusic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.service.UserService;

@RestController
@RequestMapping("/api/artist")
public class MusicArtistUserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<?> createArtistUser(@RequestBody CreateUserRequestDTO createUserRequestDTO, @RequestHeader("Authorization") String tokenJWT) {
        try {

            // Eliminar el prefijo Bearer
            String token = tokenJWT.replace("Bearer ", "");

            // Validar el token JWT
            authorizationService.verify(token);

            // Delegar la creación del usuario a la capa de servicio
            userService.createArtist(createUserRequestDTO);

            // Retornar el código 201 si la creación fue exitosa
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre una excepción, retornar el código 409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
