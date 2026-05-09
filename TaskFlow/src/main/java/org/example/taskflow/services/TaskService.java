package org.example.taskflow.services;

import org.example.taskflow.dto.ApiResponse;
import org.example.taskflow.dto.TaskLists;
import org.example.taskflow.model.Task;
import org.example.taskflow.dao.TaskDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class TaskService {

    private final TaskDAO taskDAO = new TaskDAO();

    public ApiResponse addTask(Long userId, Task task){
        return taskDAO.addTask(userId, task);
    }


    public TaskLists getTasks(Long userId){
        return taskDAO.getTasks(userId);
    }


    public ApiResponse deleteTask(Long taskId){
        return taskDAO.deleteTask(taskId);
    }


    public ApiResponse editTask(Task task){

        return taskDAO.editTask(task);
    }

    public TaskLists searchTask(Long id, String search){
        return taskDAO.searchTasks(id, search);
    }


    public Long getDaysRemaining(String dueDate){
        return ChronoUnit.DAYS.between(LocalDate.parse(dueDate), LocalDate.now());
    }
}
