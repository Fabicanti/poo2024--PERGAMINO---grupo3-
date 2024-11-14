package ar.edu.unnoba.poo2024.allmusic.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ar.edu.unnoba.poo2024.allmusic.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicEnthusiastUser;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;

public class AuthenticationServiceImpTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImp authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate() throws Exception {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("testuser", "password");
        MusicEnthusiastUser user = new MusicEnthusiastUser();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtTokenUtil.generateToken("testuser")).thenReturn("token");

        String token = authenticationService.authenticate(requestDTO);

        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    public void testAuthenticateInvalidPassword() throws Exception {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("testuser", "password");
        MusicEnthusiastUser user = new MusicEnthusiastUser();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(requestDTO));
    }

    @Test
    public void testAuthenticateUserNotFound() throws Exception {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("testuser", "password");

        when(userService.findByUsername("testuser")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(requestDTO));
    }
}
