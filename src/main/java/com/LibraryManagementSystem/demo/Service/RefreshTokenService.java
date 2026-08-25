package com.LibraryManagementSystem.demo.Service;

import com.LibraryManagementSystem.demo.Entity.RefreshToken;
import com.LibraryManagementSystem.demo.Entity.User;
import com.LibraryManagementSystem.demo.Repository.RefreshTokenRepository;
import com.LibraryManagementSystem.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh.expiration}")
    private Long tokenDuration;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository){
        this.refreshTokenRepository=refreshTokenRepository;
        this.userRepository=userRepository;
    }
    public RefreshToken createRefreshToken(String username){
        User use=userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Username not found"));
        RefreshToken token = new RefreshToken();
        token.setUser(use);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(tokenDuration));
        return refreshTokenRepository.save(token);
    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
    public RefreshToken verifyToken(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Token expired");
        }
        return token;
    }
    }
