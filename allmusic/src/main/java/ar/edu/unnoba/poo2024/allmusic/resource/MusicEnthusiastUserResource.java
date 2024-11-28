package ar.edu.unnoba.poo2024.allmusic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.service.UserService;

@RestController
@RequestMapping("/api/enthusiast")
public class MusicEnthusiastUserResource {

     @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createEnthusiastUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        try {

            // Delegar la creación del usuario a la capa de servicio
            userService.createEnthusiast(createUserRequestDTO);

            // Retornar el código 201 si la creación fue exitosa
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre una excepción, retornar el código 409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
