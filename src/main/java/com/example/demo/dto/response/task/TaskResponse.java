package com.example.demo.dto.response.task;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
    int taskId;
    int parentTaskId;
    String title;
    String content;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int status;
}
