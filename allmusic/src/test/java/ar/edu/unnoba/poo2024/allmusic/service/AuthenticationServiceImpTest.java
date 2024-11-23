/*
package ar.edu.unnoba.poo2024.allmusic.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicEnthusiastUser;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationServiceImpTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImp authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("testuser", "password");
        MusicEnthusiastUser user = new MusicEnthusiastUser();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtTokenUtil.generateToken("testuser")).thenReturn("token");

        String token = authenticationService.authenticate(requestDTO);

        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    public void testAuthenticateInvalidPassword() {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("testuser", "password");

        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(BadCredentialsException.class);

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(requestDTO));
    }

    @Test
    public void testAuthenticateUserNotFound() {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("testuser", "password");

        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(BadCredentialsException.class);

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(requestDTO));
    }
}
 */