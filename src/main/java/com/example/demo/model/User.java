package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    int userId;
    String name;
    String email;
    String password;
    String avatar;
    String description;
    int status;
    LocalDateTime createdAt;
    int createdBy;
    LocalDateTime updatedAt;
    int updatedBy;
}

//user_id INT AUTO_INCREMENT PRIMARY KEY,
//name VARCHAR(255) NOT NULL,
//email VARCHAR(255) UNIQUE NOT NULL,
//password VARCHAR(255) NOT NULL,
//avatar VARCHAR(255),
//description VARCHAR(255),
//status INT,
//note TEXT,
//delete_flg INT DEFAULT 0,
//created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//created_by VARCHAR(255),
//updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//updated_by VARCHAR(255)

