package ar.edu.unnoba.poo2024.allmusic.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Artist")
@NoArgsConstructor
@AllArgsConstructor
public class MusicArtistUser extends User{

    private String artistName;

    public MusicArtistUser(String username, String password, String artistName) {
        super(username, password);
        this.artistName = artistName;
    }

    @Override
    public boolean canCreateSongs() {
       return true;
    }

    
}