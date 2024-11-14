package ar.edu.unnoba.poo2024.allmusic.util;

import org.springframework.stereotype.Component;
import com.password4j.Password;


@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return Password.hash(rawPassword).withBCrypt().getResult();
    }

    public boolean verify(String rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withBCrypt();
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return verify(rawPassword, encodedPassword);
    }
}
