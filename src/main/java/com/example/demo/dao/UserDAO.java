package com.example.demo.dao;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    private final UserMapper userMapper;

    public UserDAO(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int createUser(CreateUserRequest request) {
        return userMapper.createUser(request);
    }

    public UserResponse getUserById(int userId) {
        return userMapper.getUserById(userId);
    }

    public UserResponse getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public int updateUser(UpdateUserRequest request) {
        return userMapper.updateUser(request);
    }

    public int deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    public boolean existUser(String email) {
        return userMapper.existByEmail(email) > 0;
    }
}
