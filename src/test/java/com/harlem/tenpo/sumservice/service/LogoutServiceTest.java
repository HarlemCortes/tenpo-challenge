package com.harlem.tenpo.sumservice.service;

import com.harlem.tenpo.sumservice.model.Token;
import com.harlem.tenpo.sumservice.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private LogoutService logoutService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        logoutService = new LogoutService(tokenRepository);
    }

    @Test
    public void testLogoutWithValidToken() {
        Authentication authentication = mock(Authentication.class);

        String validToken = "validJwtToken";
        String authHeader = "Bearer " + validToken;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(tokenRepository.findByToken(validToken)).thenReturn(Optional.of(new Token())); // Assuming Token class

        logoutService.logout(request, response, authentication);

        // Verify that the token was marked as expired and revoked, and the repository was called
        verify(tokenRepository).findByToken(validToken);
        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    public void testLogoutWithInvalidToken() {
        Authentication authentication = mock(Authentication.class);

        String invalidToken = "invalidJwtToken";
        String authHeader = "Bearer " + invalidToken;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(tokenRepository.findByToken(invalidToken)).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        // Verify that the token was not found, so repository save
        verify(tokenRepository).findByToken(invalidToken);
        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    public void testLogoutWithoutAuthorizationHeader() {
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        // Verify that the tokenRepository methods
        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));

    }
}
