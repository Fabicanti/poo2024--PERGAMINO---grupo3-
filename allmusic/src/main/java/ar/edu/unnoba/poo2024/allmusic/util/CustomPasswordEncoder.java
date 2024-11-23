package ar.edu.unnoba.poo2024.allmusic.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.password4j.Password;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return Password.hash(rawPassword).withBCrypt().getResult();
    }

    public boolean verify(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withBCrypt();
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withBCrypt();
    }

}
