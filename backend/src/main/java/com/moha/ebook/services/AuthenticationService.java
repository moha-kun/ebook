package com.moha.ebook.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.ebook.dao.TokenRepository;
import com.moha.ebook.dao.UtilisateurRepository;
import com.moha.ebook.dto.AuthenticationRequest;
import com.moha.ebook.dto.AuthenticationResponse;
import com.moha.ebook.dto.RegisterRequest;
import com.moha.ebook.entities.Token;
import com.moha.ebook.entities.TokenType;
import com.moha.ebook.entities.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UtilisateurRepository utilisateurRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var utilisateur = Utilisateur.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = utilisateurRepository.save(utilisateur);
        var jwtToken = jwtService.generateToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken(utilisateur);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken(utilisateur);
        revokeAllUserTokens(utilisateur);
        saveUserToken(utilisateur, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(Utilisateur utilisateur) {
        var validUserTokens = tokenRepository.findAllValidTokensByUtilisateur(utilisateur.getIdUtilisateur());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(Utilisateur savedUser, String jwtToken) {
        var token = Token.builder()
                .utilisateur(savedUser)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(refreshToken);

        if(email != null) {
            var user = utilisateurRepository.findByEmail(email)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
