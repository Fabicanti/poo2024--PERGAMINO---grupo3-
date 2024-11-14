package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String authenticate(AuthenticationRequestDTO requestDTO) {
        User user;
        try {
            user = userService.findByUsername(requestDTO.getUsername());
        } catch (Exception e) {
            throw new RuntimeException("Error finding user", e);
        }
        if (user == null || !passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtTokenUtil.generateToken(user.getUsername());
    }
}