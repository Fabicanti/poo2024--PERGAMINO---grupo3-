package ar.edu.unnoba.poo2024.allmusic.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "gHuD8bX5V2aR9zQ1LpNcKoJe3WyMfTbCv"; // Cambia a una clave segura
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET_KEY);

    public String generateToken(String subject, String userType) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // Expira en 10 días
                .withClaim("role", userType)
                .sign(ALGORITHM);
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    public String getUserType(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("role").asString();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String role = decodedJWT.getClaim("role").asString();
        return role != null ? 
                List.of(new SimpleGrantedAuthority(role)) : 
                List.of();
    }
}
