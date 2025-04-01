package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    int userId;
    String username;
    String email;
    String password;
    String displayName;
    String avatar;
    String description;
    int status;
    String note;
    int deleteFlg;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
}

//user_id INT AUTO_INCREMENT PRIMARY KEY,
//username VARCHAR(125) UNIQUE NOT NULL,
//email VARCHAR(255) NOT NULL,
//password VARCHAR(255) NOT NULL,
//display_name VARCHAR(255) NOT NULL,
//avatar VARCHAR(255),
//description VARCHAR(255),
//status INT,
//note TEXT,
//delete_flg INT NOT NULL DEFAULT 0,
//created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//created_by VARCHAR(255),
//updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//updated_by VARCHAR(255)

