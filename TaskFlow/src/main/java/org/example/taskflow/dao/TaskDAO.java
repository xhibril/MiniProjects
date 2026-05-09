package org.example.taskflow.dao;

import org.example.taskflow.dto.ApiResponse;
import org.example.taskflow.dto.TaskLists;
import org.example.taskflow.model.Task;
import org.example.taskflow.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public ApiResponse addTask(Long id, Task task){
        String query = "INSERT INTO tasks (title, description, status, priority, assigned_user_id, created_at, due_date, completed_at)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(query);
            ps.setString(1,task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus());
            ps.setString(4, task.getPriority());
            ps.setLong(5, id);
            ps.setString(6, task.getCreatedAt());
            ps.setString(7, task.getDueDate());
            ps.setString(8, task.getCompletedAt());
            ps.executeUpdate();

            return new ApiResponse("Task added successfully", true);

        } catch (SQLException e) {
            return new ApiResponse("Something went wrong, please try again", false);
        }
    }


    public TaskLists getTasks(Long id){
        String query = "SELECT * FROM tasks WHERE assigned_user_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            return getTasksHelper(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public TaskLists searchTasks(Long id, String search){
        String query = "SELECT * FROM tasks WHERE assigned_user_id = ? AND title = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(query);
            ps.setLong(1, id);
            ps.setString(2, search);
            ResultSet rs = ps.executeQuery();

            return getTasksHelper(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private TaskLists getTasksHelper(ResultSet rs) throws SQLException {

        List<Task> toDo = new ArrayList<>();
        List<Task> inProgress = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        TaskLists taskLists = new TaskLists();


        while(rs.next()){
            Task task = new Task();

            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setStatus(rs.getString("status"));
            task.setPriority(rs.getString("priority"));
            task.setAssignedUserId(rs.getLong("assigned_user_id"));
            task.setCreatedAt(rs.getString("created_at"));
            task.setDueDate(rs.getString("due_date"));
            task.setCompletedAt(rs.getString("completed_at"));

            String status = task.getStatus();

            if(status == null){
                continue;
            }

            switch (status) {
                case "TODO" -> toDo.add(task);
                case "IN_PROGRESS" -> inProgress.add(task);
                case "DONE" -> done.add(task);
            }
        }

        taskLists.setTodo(toDo);
        taskLists.setInProgress(inProgress);
        taskLists.setDone(done);

        return taskLists;
    }







    public ApiResponse deleteTask(Long taskId) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(query);
            ps.setLong(1, taskId);
            ps.executeUpdate();

            return new ApiResponse("Task successfully deleted", true);
        } catch (SQLException e) {
            return new ApiResponse("Could not delete task, please try again", false);
        }
    }


    public ApiResponse editTask(Task task){
        String query = "UPDATE tasks SET title = ?, description = ?, status = ?, priority = ?, due_date = ?, completed_at = ? WHERE id = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(query);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus());
            ps.setString(4, task.getPriority());
            ps.setString(5, task.getDueDate());
            ps.setString(6, task.getCompletedAt());
            ps.setLong(7, task.getId());
            ps.executeUpdate();
            return new ApiResponse("Task successfully updated", true);

        } catch (SQLException e) {
            return new ApiResponse("Could not update task, please try again", false);
        }
    }
}
