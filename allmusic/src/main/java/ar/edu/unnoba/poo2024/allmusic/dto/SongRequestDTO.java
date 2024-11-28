package ar.edu.unnoba.poo2024.allmusic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongRequestDTO {
    private String name;
    private String genre;
    private String artist;
}

