package com.example.demo.dao;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {
    private final UserMapper userMapper;

    public UserDAO(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.getAllUsers();
    }

    public int createUser(User request) {
        return userMapper.createUser(request);
    }

    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public int updateUser(User request) {
        return userMapper.updateUser(request);
    }

    public int deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    public boolean existUser(String email) {
        return userMapper.existByEmail(email) > 0;
    }
}
