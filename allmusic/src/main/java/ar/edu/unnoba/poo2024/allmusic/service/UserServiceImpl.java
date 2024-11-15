package ar.edu.unnoba.poo2024.allmusic.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.model.MusicEnthusiastUser;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.repository.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        MusicArtistUser artistUser = modelMapper.map(createUserRequestDTO, MusicArtistUser.class);
        System.out.println("Before encoding: " + artistUser.getPassword());
        artistUser.setPassword(passwordEncoder.encode(artistUser.getPassword()));
        System.out.println("After encoding: " + artistUser.getPassword());
        return userRepository.save(artistUser);
    }

    @Override
    public User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        MusicEnthusiastUser enthusiastUser = modelMapper.map(createUserRequestDTO, MusicEnthusiastUser.class);
        System.out.println("Before encoding: " + enthusiastUser.getPassword());
        enthusiastUser.setPassword(passwordEncoder.encode(enthusiastUser.getPassword()));
        System.out.println("After encoding: " + enthusiastUser.getPassword());
        return userRepository.save(enthusiastUser);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities() // Convierte los roles a GrantedAuthority
        );
    }

}
