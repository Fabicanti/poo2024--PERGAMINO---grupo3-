package ar.edu.unnoba.poo2024.allmusic.util;

import org.springframework.stereotype.Component;
import com.password4j.Password;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return Password.hash(rawPassword).addSalt("someSalt").withBCrypt().getResult();
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withBCrypt();
    }
}
