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
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success("User created successfully", userService.createUser(request)));
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<UserResponse>>> searchUsers(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.searchUsers(query)));
    }

    @GetMapping("/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", userService.getUserById(userId)));
    }

    @GetMapping("/me")
    ResponseEntity<ApiResponse<UserResponse>> getUserProfile() {
        return ResponseEntity.ok(ApiResponse.success("Current user fetched successfully", userService.getCurrentUser()));
    }

    @PutMapping("/me")
    ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", userService.updateUser(request)));
    }

    @DeleteMapping("/me")
    ResponseEntity<ApiResponse<String>> deleteCurrentUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}
