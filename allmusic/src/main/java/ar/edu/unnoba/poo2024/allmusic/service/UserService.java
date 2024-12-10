package ar.edu.unnoba.poo2024.allmusic.service;

import java.util.List;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;

public interface UserService {

    User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception;
    User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception;
    User findByUsername(String username);
    User findById(Long id);
    List<User> findAllUsers();

}
