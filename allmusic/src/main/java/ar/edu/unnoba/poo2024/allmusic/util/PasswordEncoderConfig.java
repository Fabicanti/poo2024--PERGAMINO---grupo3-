package ar.edu.unnoba.poo2024.allmusic.util;

import com.password4j.Password;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordEncoderConfig {

    // Este es un bean que usa Password4j para hash y verificación de contraseñas
    @Bean
    public Password4jEncoder passwordEncoder() {
        return new Password4jEncoder();
    }

    // Usamos Password4j para hacer el hash de la contraseña
    public static class Password4jEncoder {

        // Método para codificar la contraseña
        public String encode(String password) {
            return Password.hash(password).withBCrypt().getResult();
        }

        // Método para verificar si la contraseña es válida
        public boolean matches(String password, String hash) {
            return Password.check(password, hash).withBCrypt();
        }
    }
}

