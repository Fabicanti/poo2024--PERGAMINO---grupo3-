package ar.edu.unnoba.poo2024.allmusic.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/**", "/api/users/**", "/api/song/**", "/api/enthusiast/**", "/api/artist/**", "/h2-console/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable() // Desactiva CSRF para simplificar en APIs REST
            .headers().frameOptions().sameOrigin() // Permite que la consola H2 se cargue en un iframe
            .and()
            .httpBasic(); // Autenticación básica para facilitar el proceso sin un formulario

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
            .password(passwordEncoder.encode("123")) // Usa el codificador de contraseñas configurado
            .roles("USER")
            .build());
        return manager;
    }
}
