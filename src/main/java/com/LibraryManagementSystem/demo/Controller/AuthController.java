package com.LibraryManagementSystem.demo.Controller;

import com.LibraryManagementSystem.demo.Entity.RefreshToken;
import com.LibraryManagementSystem.demo.Service.JwtService;
import com.LibraryManagementSystem.demo.Service.RefreshTokenService;
import com.LibraryManagementSystem.demo.dto.LoginRequest;
import com.LibraryManagementSystem.demo.dto.LoginResponse;
import com.LibraryManagementSystem.demo.dto.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        String token = jwtService.generateToken(loginRequest.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());
        return ResponseEntity.ok(
                new LoginResponse(token,
                        refreshToken.getToken())
        );
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshToken refreshToken= refreshTokenService
                .findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(()->new RuntimeException("Token not found"));
        refreshTokenService.verifyToken(refreshToken);
        String accessToken = jwtService.generateToken(
                refreshToken.getUser().getUsername()
        );
        return ResponseEntity.ok(
                new LoginResponse(
                        accessToken,
                        refreshToken.getToken())

                );

    }
}



