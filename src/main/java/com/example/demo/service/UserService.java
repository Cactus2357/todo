package com.example.demo.service;

import com.example.demo.dao.RoleDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.enums.RoleEnum;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.User;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserDAO userDAO;
    RoleDAO roleDAO;
    PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = toUser(request);

        boolean userExisted = userDAO.existUser(request.getEmail());
        if (userExisted) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        roleDAO.updateUserRole(user.getUserId(), RoleEnum.USER.getRoleId());
        int rows = userDAO.createUser(user);

        if (rows == 1) {
            user = userDAO.getUserById(user.getUserId());
            return toUserResponse(user);
        }

        throw new AppException(ErrorCode.UNCATEGORIZED);
    }

    public UserResponse getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        return toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> userList = userDAO.findAll();
        return userList.stream().map(this::toUserResponse).toList();
    }

    private User toUser(@NonNull CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    private UserResponse toUserResponse(@NonNull User user) {
        return new UserResponse(user.getUserId(), user.getName(), user.getEmail(), user.getAvatar(), user.getDescription(), user.getStatus(), user.getCreatedAt());
    }
}
