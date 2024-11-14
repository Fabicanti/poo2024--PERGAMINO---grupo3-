package ar.edu.unnoba.poo2024.allmusic.service;
import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;

public interface AuthenticationService {
    String authenticate(AuthenticationRequestDTO requestDTO) throws Exception;
}