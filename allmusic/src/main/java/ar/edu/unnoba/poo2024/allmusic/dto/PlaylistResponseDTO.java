package ar.edu.unnoba.poo2024.allmusic.dto;

import java.util.List;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private List<Song> songs;
}
