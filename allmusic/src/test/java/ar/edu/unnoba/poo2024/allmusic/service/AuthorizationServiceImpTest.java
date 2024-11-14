package ar.edu.unnoba.poo2024.allmusic.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;

public class AuthorizationServiceImpTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthorizationServiceImp authorizationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerify() {
        String token = "validToken";

        when(jwtTokenUtil.validateToken(token)).thenReturn(true);

        assertDoesNotThrow(() -> authorizationService.verify(token));
    }

    @Test
    public void testVerifyInvalidToken() {
        String token = "invalidToken";

        when(jwtTokenUtil.validateToken(token)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authorizationService.verify(token));
    }
}
