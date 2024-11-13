package ar.edu.unnoba.poo2024.allmusic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.repository.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoderConfig;


@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoderConfig.Password4jEncoder password4jEncoder;
    
    @Override
    public void create(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("User ya existe");
        }
        user.setPassword(password4jEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
    
