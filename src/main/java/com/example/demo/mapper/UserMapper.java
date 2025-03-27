package com.example.demo.mapper;

import com.example.demo.model.User;

import java.util.List;

public interface UserMapper {
    List<User> getAllUsers();

    int createUser(User user);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    int updateUser(User user);

    int deleteUser(Integer userId);

    int existByEmail(String email);
}
