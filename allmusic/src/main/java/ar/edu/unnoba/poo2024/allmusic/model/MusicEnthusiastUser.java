package ar.edu.unnoba.poo2024.allmusic.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Enthusiast")
@NoArgsConstructor
public class MusicEnthusiastUser extends User{

    public MusicEnthusiastUser(String username, String password, String artistName) {
        super(username, password);
    }
    
    @Override
    public boolean canCreateSongs() {
        return false;
    }
}
