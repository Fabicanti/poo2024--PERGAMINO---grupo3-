/*package ar.edu.unnoba.poo2024.allmusic.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateUserRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicEnthusiastUser;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.repository.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;
import ar.edu.unnoba.poo2024.allmusic.util.ResourceNotFoundException;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder password4jEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO("testuser", "password");
        MusicEnthusiastUser user = new MusicEnthusiastUser("testuser", "password");

        when(modelMapper.map(requestDTO, MusicEnthusiastUser.class)).thenReturn(user);
        when(password4jEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(requestDTO);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindByUsername() {
        MusicEnthusiastUser user = new MusicEnthusiastUser("testuser", "password");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("testuser"));
    }
}
*/