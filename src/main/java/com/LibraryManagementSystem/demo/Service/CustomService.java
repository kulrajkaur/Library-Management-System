package com.LibraryManagementSystem.demo.Service;

import com.LibraryManagementSystem.demo.Entity.User;
import com.LibraryManagementSystem.demo.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user= userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }


}
