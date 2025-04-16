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

    public List<Task> getAllUserTasks(Integer userId, Integer groupId) {
        return taskMapper.getAllTasksByUserId(userId, groupId);
    }

    public Task getTaskById(int taskId, int userId) {
        return taskMapper.getTaskById(taskId, userId);
    }

    public Task getTaskById(int taskId, Integer userId) {
        return taskMapper.getTaskById(taskId, userId);
    }

    public int createTask(Task task, int userId) {
        task.setStatus(Const.STATUS_TASK_PROGRESS);
        taskMapper.insertTask(task, userId);
        taskMapper.insertTaskRelation(task.getTaskId(), userId, null);
        return task.getTaskId();
    }

    public int updateTask(Task task, int userId) {
        taskMapper.updateTask(task, userId);
        return task.getTaskId();
    }

    public int deleteTask(int taskId, int userId) {
        taskMapper.deleteTask(taskId, userId);
        return taskId;
    }

    public int updateTaskStatus(int taskId, int taskStatus, int userId) {
        taskMapper.updateTaskStatus(taskId, taskStatus, userId);
        return taskId;
    }
}
