package ar.edu.unnoba.poo2024.allmusic.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PlayList {
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "PlayList_Songs",
		joinColumns = @JoinColumn(name = "playlist"),
		inverseJoinColumns = @JoinColumn(name = "song")
	)
    @Builder.Default
    private List<Song> songs = new ArrayList<Song>();
    
    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;

}
