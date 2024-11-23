package ar.edu.unnoba.poo2024.allmusic.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {

    private String username;
    private String password;
    private String userType;
}