package ar.edu.unnoba.poo2024.allmusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import org.springframework.lang.NonNull;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM MusicArtistUser u WHERE u.username = :username")
    Optional<MusicArtistUser> findArtistByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);
    
    @NonNull
    Optional<User> findById(@NonNull Long id);
}
