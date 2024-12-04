package org.zurika.healthappointment;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.zurika.healthappointment.model.*;
import org.zurika.healthappointment.repository.*;
import org.zurika.healthappointment.service.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(UserRole.PATIENT);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService
                .addUser(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        String.valueOf(user.getRole())
                );
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals(UserRole.PATIENT, createdUser.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdatePatientInfo() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");
        existingUser.setEmail("old@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        userService.updatePatientInfo(1L, "NewFirstName", "NewLastName", "new@example.com");

        verify(userRepository, times(1)).save(existingUser);
        assertEquals("NewFirstName", existingUser.getFirstName());
        assertEquals("NewLastName", existingUser.getLastName());
        assertEquals("new@example.com", existingUser.getEmail());
    }
}
