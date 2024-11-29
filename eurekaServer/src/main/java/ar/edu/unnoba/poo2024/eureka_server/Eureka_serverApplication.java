package ar.edu.unnoba.poo2024.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka_serverApplication {

	public static void main(String[] args) {
		SpringApplication.run(Eureka_serverApplication.class, args);
	}

}
