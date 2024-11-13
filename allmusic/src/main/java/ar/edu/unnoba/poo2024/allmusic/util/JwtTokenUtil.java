package ar.edu.unnoba.poo2024.allmusic.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component  // Esto convierte a JwtTokenUtil en un bean de Spring
public class JwtTokenUtil {

    private static final String SECRET_KEY = "your-secret-key"; // Cambia esto a una clave segura
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET_KEY);

    public String generateToken(String subject) {
        return "Bearer " + JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // Expira en 10 días
                .sign(ALGORITHM);
    }

    public boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            verifier.verify(token.replace("Bearer ", ""));
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        DecodedJWT decodedJWT = JWT.decode(token.replace("Bearer ", ""));
        return decodedJWT.getSubject();
    }
}
