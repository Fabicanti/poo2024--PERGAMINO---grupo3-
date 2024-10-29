package ar.edu.unnoba.poo2024.allmusic.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Artist")
public class MusicArtistUser extends User{

    @Override
    public boolean canCreateSongs() {
       return true;
    }
    
}