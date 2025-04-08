package com.example.demo.mapper;

import com.example.demo.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    List<Task> getAllTasksByUserId(@Param("userId") int userId);

    Task getTaskById(@Param("taskId") int taskId, @Param("userId") Integer userId);

    int createTask(@Param("task") Task task, @Param("userId") int userId);

    int updateTask(@Param("task") Task task, @Param("userId") int userId);

    int updateTaskStatus(@Param("taskId") int taskId, @Param("statusId") int statusId, @Param("userId") int userId);

    int deleteTask(@Param("taskId") int taskId, @Param("userId") int userId);
}
