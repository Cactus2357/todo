package com.example.demo.configuration;

import com.example.demo.dao.RoleDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.enums.RoleEnum;
import com.example.demo.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationContextConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USERNAME = "admin";

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "123@123A";

    @NonFinal
    static final String ADMIN_DISPLAY_NAME = "Administrator";

    @Bean
    @Transactional
    ApplicationRunner init(UserDAO userDAO, RoleDAO roleDAO) {
        log.info("Initializing application...");

        return args -> {
            if (userDAO.existUser(ADMIN_USERNAME, null)) {
                log.info("Application initialization completed...");
                return;
            }

            createAdminAccount(userDAO, roleDAO);

            log.warn("Admin account has been created with default password [@{}: {}]. Please change it.", ADMIN_USERNAME, ADMIN_PASSWORD);

            log.info("Application initialization completed...");
        };
    }

    private void createAdminAccount(UserDAO userDAO, RoleDAO roleDAO) {
        User user = User.builder()
                .username(ADMIN_USERNAME)
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .displayName(ADMIN_DISPLAY_NAME)
                .build();

        userDAO.createUser(user);
        roleDAO.updateUserRole(user.getUserId(), RoleEnum.ADMIN.getRoleId());
    }

}
