package com.example.demo.service;

import com.example.demo.dao.RoleDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.enums.RoleEnum;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.User;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
    public UserResponse createUser(CreateUserRequest request) {

        boolean userExisted = userDAO.existUser(request.getUsername(), request.getUsername());

        if (userExisted) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = toUser(request);

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
    public UserResponse updateUser(UpdateUserRequest request) {

        User user = toUser(request);
        user.setUserId(getCurrentUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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

    private User toUser(@NonNull CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDisplayName(request.getDisplayName());
        return user;
    }

    private User toUser(@NonNull UpdateUserRequest request) {
        User user = new User();
        user.setUserId(request.getUserId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDisplayName(request.getDisplayName());
        user.setAvatar(request.getAvatar());
        user.setDescription(request.getDescription());
        user.setStatus(request.getStatus());
        return user;
    }

    private UserResponse toUserResponse(@NonNull User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getAvatar(),
                user.getDescription(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}
