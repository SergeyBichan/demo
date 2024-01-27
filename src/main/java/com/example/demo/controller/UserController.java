package com.example.demo.controller;

import com.example.demo.entity.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDto> getUserbyId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> saveUserToDb(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.save(userDto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.remove(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUserById(
            @PathVariable("id")Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @PutMapping("/users/{userId}/departments/{departmentId}")
    public ResponseEntity<UserDto> addUserToDepartment(
            @PathVariable Long userId, @PathVariable Long departmentId){
        return ResponseEntity.ok(userService.addUserToDepartment(userId, departmentId));
    }
}
