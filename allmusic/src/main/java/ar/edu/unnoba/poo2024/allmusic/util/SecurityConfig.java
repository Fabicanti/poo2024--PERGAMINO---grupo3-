package ar.edu.unnoba.poo2024.allmusic.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/h2-console/**").authenticated()  // Requiere autenticación para la consola de H2
                .requestMatchers("/favicon.ico", "/static/**", "/public/**", "/css/**", "/js/**", "/error").permitAll()  // Rutas públicas sin autenticación
                .anyRequest().authenticated()  // El resto de las solicitudes requieren autenticación
            )
            .csrf((csrf) -> csrf.disable())  // Desactiva CSRF para la consola de H2
            .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()))  // Permite cargar el iframe de la consola de H2
            .formLogin();  // Habilita el formulario de inicio de sesión predeterminado

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
            .password("{noop}password")  // {noop} indica que no se usará ningún codificador de contraseñas
            .roles("USER")
            .build());
        return manager;
    }
}
