package ar.edu.unnoba.poo2024;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import ar.edu.unnoba.poo2024.allmusic.util.CustomPasswordEncoder;

@SpringBootApplication
public class AllmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllmusicApplication.class, args);
		/*String rawPassword = "password_artist"; // Cambia según lo necesites
		CustomPasswordEncoder encoder = new CustomPasswordEncoder();
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println("Contraseña encriptada: " + encodedPassword);*/
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
