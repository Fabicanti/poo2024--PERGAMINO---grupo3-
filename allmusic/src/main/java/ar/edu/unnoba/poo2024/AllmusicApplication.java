package ar.edu.unnoba.poo2024;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AllmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllmusicApplication.class, args);
	}

	@Bean
	public String passwordEncoder() {
		return "passwordEncoder";
	}

	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
