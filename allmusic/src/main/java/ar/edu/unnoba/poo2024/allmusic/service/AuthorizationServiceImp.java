package ar.edu.unnoba.poo2024.allmusic.service;

import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthorizationServiceImp implements AuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    public User authorize(String token) throws Exception {
        if (!jwtTokenUtil.verify(token)) {
            throw new Exception("Invalid token");
        }
        String username = jwtTokenUtil.getSubject(token);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }
}
