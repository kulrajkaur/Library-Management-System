package com.LibraryManagementSystem.demo.Config;

import com.LibraryManagementSystem.demo.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService){
        this.jwtService=jwtService;
        this.userDetailsService= userDetailsService;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{
    String  path = request.getServletPath();
        if (path.equals("/user/add") || path.equals("/student/add") ||
                path.equals("/auth/login") || path.equals("/auth/refresh")) {
            chain.doFilter(request, response);
            return;
        }
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            System.out.println("AUTH HEADER = [" + authHeader + "]");
            System.out.println("TOKEN = [" + token + "]");

            username = jwtService.extractUsername(token);
        }
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    }




