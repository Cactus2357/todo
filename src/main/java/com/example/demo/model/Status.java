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
}
