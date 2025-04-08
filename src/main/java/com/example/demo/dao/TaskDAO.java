package com.example.demo.dao;

import com.example.demo.constant.Const;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO {
    private final TaskMapper taskMapper;

    public TaskDAO(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<Task> getAllUserTasks(int userId) {
        return taskMapper.getAllTasksByUserId(userId);
    }

    public Task getTaskById(int taskId, int userId) {
        return taskMapper.getTaskById(taskId, userId);
    }

    public Task getTaskById(int taskId) {
        return taskMapper.getTaskById(taskId, null);
    }

    public int createTask(Task task, int userId) {
        task.setStatus(Const.STATUS_TASK_PROGRESS);
        return taskMapper.createTask(task, userId);
    }

    public int updateTask(Task task, int userId) {
        return taskMapper.updateTask(task, userId);
    }

    public int deleteTask(int taskId, int userId) {
        return taskMapper.deleteTask(taskId, userId);
    }

    public int updateTaskStatus(int taskId, int taskStatus, int userId) {
        return taskMapper.updateTaskStatus(taskId, taskStatus, userId);
    }
}
