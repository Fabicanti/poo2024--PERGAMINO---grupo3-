package ar.edu.unnoba.poo2024.allmusic.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Artist")
public class MusicArtistUser extends User{

    public MusicArtistUser() {
        super();
    }

    public MusicArtistUser(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean canCreateSongs() {
       return true;
    }

    
}