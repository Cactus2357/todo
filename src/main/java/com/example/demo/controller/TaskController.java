package com.example.demo.controller;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.request.task.CreateTaskRequest;
import com.example.demo.dto.request.task.UpdateTaskRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.task.TaskResponse;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks(UserService.getCurrentUserId()));
    }

    @GetMapping("/{taskId}")
    ResponseEntity<TaskResponse> getAllTasks(@PathVariable int taskId) {
        TaskResponse taskResponse = taskService.getTaskById(taskId);
        if (taskResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping
    ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.getTaskById(taskService.createTask(request)));
    }

    @PutMapping
    ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.getTaskById(taskService.updateTask(request)));
    }

    @DeleteMapping("/{taskId}")
    ResponseEntity<?> deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

}
