package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public String authenticate(AuthenticationRequestDTO request) {
        //necesito verificar que el usuario y la contraseña sean correctos
        //y si son correctos, genero el token pasando el username y el tipo de usuario
        //si no son correctos, lanzo una excepción
        // Verificar si el usuario existe
        User user = userService.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Validar la contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generar y devolver el token
        String userType = user.getUserType();
        return jwtTokenUtil.generateToken(user.getId().toString(), userType);
    }
}