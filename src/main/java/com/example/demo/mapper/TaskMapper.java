package com.example.demo.mapper;

import com.example.demo.model.Task;
import jakarta.annotation.Nullable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    List<Task> getAllTasksByUserId(@Nullable @Param("userId") Integer userId, @Nullable @Param("groupId") Integer groupId);

    Task getTaskById(@Param("taskId") int taskId, @Param("userId") Integer userId);

    int insertTask(@Param("task") Task task, @Param("userId") int userId);

    int insertTaskRelation(@Param("taskId") int taskId, @Nullable @Param("userId") Integer userId, @Nullable @Param("groupId") Integer groupId);

    int updateTask(@Param("task") Task task, @Param("userId") int userId);

    int updateTaskStatus(@Param("taskId") int taskId, @Param("statusId") int statusId, @Param("userId") int userId);

    int deleteTask(@Param("taskId") int taskId, @Param("userId") int userId);
}
