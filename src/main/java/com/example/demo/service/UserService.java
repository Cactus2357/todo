package com.example.demo.service;

import com.example.demo.constant.Const;
import com.example.demo.dao.RoleDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.enums.RoleEnum;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.User;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserDAO userDAO;
    RoleDAO roleDAO;
    PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public static int getCurrentUserId() {
        try {
            return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        boolean userExisted = userDAO.existUser(request.getUsername(), request.getEmail());
        if (userExisted) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Const.STATUS_USER_ACTIVE);

        int rows = userDAO.createUser(user);
        roleDAO.updateUserRole(user.getUserId(), RoleEnum.USER.getRoleId());

        if (rows == 1) {
            user = userDAO.getUserById(user.getUserId());
            return toUserResponse(user);
        }

        throw new AppException(ErrorCode.UNCATEGORIZED);
    }

    public UserResponse getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> userList = userDAO.findAll();
        return userList.stream().map(this::toUserResponse).toList();
    }

    public List<UserResponse> searchUsers(String query) {
        String[] queries = null;
        if (query != null && !query.isBlank()) {
            queries = query.trim().split("\\s+");
        }

        return userDAO.findAll(queries).stream().map(this::toUserResponse).toList();
    }

    public UserResponse getCurrentUser() {
        User user = userDAO.getUserById(getCurrentUserId());
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UserUpdateRequest request) {
        User user = toUser(request);
        user.setUserId(getCurrentUserId());
        userDAO.updateUser(user);

        return getUserById(user.getUserId());
    }

    @PreAuthorize("hasRole('admin')")
    public int deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public int deleteCurrentUser() {
        return userDAO.deleteUser(getCurrentUserId());
    }

    private User toUser(@NonNull UserCreateRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .displayName(request.getDisplayName())
                .build();
    }

    private User toUser(@NonNull UserUpdateRequest request) {
        return User.builder()
                .displayName(request.getDisplayName())
                .avatar(request.getAvatar())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();
    }

    private UserResponse toUserResponse(@NonNull User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .avatar(user.getAvatar())
                .description(user.getDescription())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
