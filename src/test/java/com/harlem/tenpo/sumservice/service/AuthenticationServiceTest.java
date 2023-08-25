package com.harlem.tenpo.sumservice.service;

import com.harlem.tenpo.sumservice.dto.AuthenticationRequest;
import com.harlem.tenpo.sumservice.dto.AuthenticationResponse;
import com.harlem.tenpo.sumservice.dto.RegisterRequest;
import com.harlem.tenpo.sumservice.model.Role;
import com.harlem.tenpo.sumservice.model.Token;
import com.harlem.tenpo.sumservice.model.User;
import com.harlem.tenpo.sumservice.repository.TokenRepository;
import com.harlem.tenpo.sumservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Create a mock RegisterRequest
        RegisterRequest request = new RegisterRequest("John", "Doe", "john@example.com", "password", Role.USER);

        // Create a mock User and set up mock behaviors
        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Create a mock JWT token and set up mock behaviors
        String jwtToken = "mockJwtToken";
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);

        // Create a mock Refresh token
        String refreshToken = "mockRefreshToken";
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(refreshToken);

        // Call the method
        AuthenticationResponse response = authService.register(request);

        // Verify interactions and assertions
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
        verify(jwtService).generateRefreshToken(any(User.class));
        verify(tokenRepository).save(any(Token.class));

        // Assert the response
        assertNotNull(response);
        assertEquals(jwtToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
    }

    @Test
    public void testAuthenticate() {
        // Create a mock AuthenticationRequest
        AuthenticationRequest request = new AuthenticationRequest("john@example.com", "password");

        // Create a mock User and set up mock behaviors
        User user = new User();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        // Create a mock JWT token and set up mock behaviors
        String jwtToken = "mockJwtToken";
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        // Mock AuthenticationManager's authenticate method to return a successful Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Call the method
        AuthenticationResponse response = authService.authenticate(request);

        // Verify interactions and assertions
        verify(userRepository).findByEmail("john@example.com");
        verify(jwtService).generateToken(user);
        verify(tokenRepository).save(any());

        // Assert the response
        assertNotNull(response);
        assertEquals(jwtToken, response.getAccessToken());
    }
}

