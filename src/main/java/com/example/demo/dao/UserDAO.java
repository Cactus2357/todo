package com.example.demo.dao;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {
    private final UserMapper userMapper;

    public UserDAO(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return findAll(null);
    }

    public List<User> findAll(String[] queries) {
        return userMapper.searchUser(queries);
    }

    public int createUser(User user) {
        return userMapper.createUser(user);
    }

    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    public boolean existUser(String username, String email) {
        return userMapper.countByUsernameAndEmail(username, email) > 0;
    }
}
