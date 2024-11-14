package ar.edu.unnoba.poo2024.allmusic.dto;

public class CreateUserRequestDTO {
    private String username;
    private String password;

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    
    public void setPassword(String password) { this.password = password; }
}

