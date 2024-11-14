package ar.edu.unnoba.poo2024.allmusic.service;

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
    public String authenticate(User user) throws Exception {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser == null || !passwordEncoder.verify(user.getPassword(), existingUser.getPassword())) {
            throw new Exception("Invalid credentials");
        }
        return "Bearer " + jwtTokenUtil.generateToken(existingUser.getUsername());
    }
}


