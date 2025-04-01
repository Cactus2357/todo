package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    int taskId;
    int parentTaskId;
    String title;
    String content;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int status;
    String note;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;

}

//task_id int auto_increment primary key,
//parent_task_id int,
//title varchar(100) not null,
//content TEXT,
//start_date DATETIME NOT NULL,
//end_date DATETIME NOT NULL,
//status int,
//note TEXT,
//delete_flg INT NOT NULL DEFAULT 0,
//created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//created_by VARCHAR(255),
//updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//updated_by VARCHAR(255)
