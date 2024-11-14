package ar.edu.unnoba.poo2024.allmusic.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public abstract boolean canCreateSongs();


}
