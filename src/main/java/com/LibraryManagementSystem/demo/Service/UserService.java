package com.LibraryManagementSystem.demo.Service;

import com.LibraryManagementSystem.demo.Entity.User;
import com.LibraryManagementSystem.demo.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    //add users//
    public User addUsers(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        return userRepository.save(user);
    }
    //list users//
    public Page<User> listUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    //Update users//
    public User updateUsers(Long userId, User user){
        User user1= userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        user1.setPassword(user.getPassword());
        return userRepository.save(user1);
    }

    //Delete user by id//
    public void deleteUser(Long userId){
        userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        userRepository.deleteById(userId);
    }

}
