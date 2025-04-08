package com.example.demo.service;

import com.example.demo.dao.RoleDAO;
import com.example.demo.dao.TaskDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.request.task.CreateTaskRequest;
import com.example.demo.dto.request.task.UpdateTaskRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.task.TaskResponse;
import com.example.demo.enums.RoleEnum;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    private TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public TaskResponse getTaskById(int taskId) {
        SecurityContext context = SecurityContextHolder.getContext();
        int userId = Integer.parseInt(context.getAuthentication().getName());
        return toTaskResponse(taskDAO.getTaskById(taskId, userId));
    }

    public List<TaskResponse> getAllTasks(int userId) {
        return taskDAO.getAllUserTasks(userId).stream().map(this::toTaskResponse).toList();
    }

    public void createTask(CreateTaskRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        int userId = Integer.parseInt(context.getAuthentication().getName());
        taskDAO.createTask(toTask(request), userId);
    }

    public void updateTask(UpdateTaskRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        int userId = Integer.parseInt(context.getAuthentication().getName());
        taskDAO.updateTask(toTask(request), userId);
    }

    public void deleteTask(int taskId) {
        SecurityContext context = SecurityContextHolder.getContext();
        int userId = Integer.parseInt(context.getAuthentication().getName());
        taskDAO.deleteTask(taskId, userId);
    }

    public void updateTaskStatus(int taskId, int statusId) {
        SecurityContext context = SecurityContextHolder.getContext();
        int userId = Integer.parseInt(context.getAuthentication().getName());
        taskDAO.updateTaskStatus(taskId, statusId, userId);
    }

    private Task toTask(@NotNull CreateTaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .parentTaskId(request.getParentTaskId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    private Task toTask(@NonNull UpdateTaskRequest request) {
        return Task.builder()
                .taskId(request.getTaskId())
                .title(request.getTitle())
                .content(request.getContent())
                .parentTaskId(request.getParentTaskId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    private TaskResponse toTaskResponse(@NonNull Task task) {
        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .content(task.getContent())
                .parentTaskId(task.getParentTaskId())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .status(task.getStatus())
                .build();
    }


}
