package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.User;

public interface UserService {

    User createArtist(CreateUserRequestDTO createUserRequestDTO) throws Exception;
    User createEnthusiast(CreateUserRequestDTO createUserRequestDTO) throws Exception;
    public User findByUsername(String username);
}
