package ar.edu.unnoba.poo2024.allmusic.resource;
import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        // Log de verificación
        System.out.println("Autenticando usuario: " + request.getUsername());
        System.out.println("Contraseña: " + request.getPassword());
        try {
            String token = authenticationService.authenticate(request);
            return ResponseEntity.ok().header("Authorization", token).body(Map.of("message", "Login successful", "token", token));
        } catch (Exception e) {
            // Log de verificación
            System.out.println("token: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
