package ar.edu.unnoba.poo2024.allmusic.util;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    public void validateToken(String token) {
        // Validar el token JWT
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido");
        }
    }
}
