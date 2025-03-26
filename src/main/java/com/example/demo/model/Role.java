package com.example.demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    int roleId;
    String roleName;
    int status;
    LocalDateTime createdAt;
    int createdBy;
    LocalDateTime updatedAt;
    int updatedBy;
}

//role_id INT AUTO_INCREMENT PRIMARY KEY,
//role_name VARCHAR(100) unique not null,
//status INT,
//delete_flg INT DEFAULT 0,
//created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//created_by VARCHAR(255),
//updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//updated_by VARCHAR(255)

