package com.example.demo.mapper;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;

public interface UserMapper {
    int createUser(CreateUserRequest params);
    UserResponse getUserById(Integer userId);
    UserResponse getUserByEmail(String email);
    int updateUser(UpdateUserRequest params);
    int deleteUser(Integer userId);
    int existByEmail(String email);
}
