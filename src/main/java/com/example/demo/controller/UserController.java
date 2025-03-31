package com.example.demo.controller;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(userService.createUser(request)).build());
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder().data(userService.getAllUsers()).build());
    }

    @GetMapping("/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(userService.getUserById(userId)).build());
    }

    @GetMapping("/profile")
    ResponseEntity<ApiResponse<UserResponse>> getUserProfile() {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(userService.getCurrentUser()).build());
    }

    @PutMapping("/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable("userId") int userId, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(userService.updateUser(userId, request)).build());
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable("userId") int userId) {
//        userService
        return ResponseEntity.ok(ApiResponse.<String>builder().build());
    }
}
