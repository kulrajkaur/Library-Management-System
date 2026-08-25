package com.LibraryManagementSystem.demo.Controller;

import com.LibraryManagementSystem.demo.Entity.User;
import com.LibraryManagementSystem.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/add")
    public User addUsers(@Valid @RequestBody User user){
        return userService.addUsers(user);
    }
    @GetMapping("/list/page")
    public Page <User> listUsers(@RequestParam int page, @RequestParam int size){
        Pageable pageable= PageRequest.of(page,size);
        return userService.listUsers(pageable);
    }
    @PutMapping("/update/{userId}")
    public User updateUsers(@PathVariable Long userId, @RequestBody User user){
        return userService.updateUsers(userId,user);
    }
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
