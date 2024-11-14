package ar.edu.unnoba.poo2024.allmusic.resource;
import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            User user = modelMapper.map(request, User.class);
            String token = authenticationService.authenticate(user);
            return ResponseEntity.ok().header("Authorization", token).body("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

