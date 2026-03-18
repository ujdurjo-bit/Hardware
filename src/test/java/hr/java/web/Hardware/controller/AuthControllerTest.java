package hr.java.web.Hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.java.web.Hardware.domain.RefreshToken;
import hr.java.web.Hardware.domain.UserInfo;
import hr.java.web.Hardware.domain.UserRole;
import hr.java.web.Hardware.dto.AuthRequestDTO;
import hr.java.web.Hardware.dto.JwtResponseDTO;
import hr.java.web.Hardware.dto.RefreshTokenRequestDTO;
import hr.java.web.Hardware.service.JwtService;
import hr.java.web.Hardware.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthController authController;

    private AuthRequestDTO authRequestDTO;

    private RefreshTokenRequestDTO refreshTokenRequestDTO;

    @BeforeEach
    void setUp() {
        authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("user");
        authRequestDTO.setPassword("password");

        refreshTokenRequestDTO = new RefreshTokenRequestDTO();
        refreshTokenRequestDTO.setToken("refresh-token");
    }

    @Test
    void testAuthenticateAndGetToken_Success() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(anyString())).thenReturn("jwt-token");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");
        when(refreshTokenService.createRefreshToken(anyString())).thenReturn(refreshToken);


        JwtResponseDTO response = authController.authenticateAndGetToken(authRequestDTO);


        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        assertEquals("refresh-token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken("user");
        verify(refreshTokenService, times(1)).createRefreshToken("user");
    }

    @Test
    void testAuthenticateAndGetToken() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("invalid user request..!!"));


        assertThrows(UsernameNotFoundException.class,
                () -> authController.authenticateAndGetToken(authRequestDTO));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(anyString());
        verify(refreshTokenService, never()).createRefreshToken(anyString());
    }

    @Test
    void testRefreshToken_Success() {

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("user");
        userInfo.setPassword("password");
        userInfo.setRoles(new HashSet<>());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");
        refreshToken.setExpiryDate(LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.UTC));
        refreshToken.setUserInfo(userInfo);

        when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(any(RefreshToken.class))).thenReturn(refreshToken);
        when(jwtService.generateToken(anyString())).thenReturn("new-jwt-token");


        JwtResponseDTO response = authController.refreshToken(refreshTokenRequestDTO);

        assertNotNull(response);
        assertEquals("new-jwt-token", response.getAccessToken());
        assertEquals("refresh-token", response.getToken());
        verify(refreshTokenService, times(1)).findByToken("refresh-token");
        verify(refreshTokenService, times(1)).verifyExpiration(refreshToken);
        verify(jwtService, times(1)).generateToken("user");
    }

    @Test
    void testRefreshToken_Failure() {

        when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class,
                () -> authController.refreshToken(refreshTokenRequestDTO));

        verify(refreshTokenService, times(1)).findByToken("refresh-token");
        verify(refreshTokenService, never()).verifyExpiration(any(RefreshToken.class));
        verify(jwtService, never()).generateToken(anyString());
    }
}