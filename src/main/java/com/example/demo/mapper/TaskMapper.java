package com.example.demo.mapper;

import com.example.demo.model.Task;

import java.util.List;

public interface TaskMapper {
    List<Task> getAllTasksByUserId(int userId);

    Task getTaskById(int userId, int taskId);

    int createTask(Task task);

    int updateTask(Task task);

    int updateTaskStatus(int taskId, String status);

    int deleteTask(Task task);
}
