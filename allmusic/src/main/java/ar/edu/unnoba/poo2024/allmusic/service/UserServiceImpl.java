package ar.edu.unnoba.poo2024.allmusic.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.repository.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder password4jEncoder;
    
    @Override
    public User create(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        User user = modelMapper.map(createUserRequestDTO, User.class);
        // Encriptar la contraseña antes de guardar el usuario
        user.setPassword(password4jEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
