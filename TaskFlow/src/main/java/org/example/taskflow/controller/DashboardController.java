package org.example.taskflow.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.taskflow.dto.DashboardStats;
import org.example.taskflow.dto.TaskLists;
import org.example.taskflow.model.Task;
import org.example.taskflow.services.AnalyticsService;
import org.example.taskflow.services.TaskService;
import org.example.taskflow.utils.CommonUtils;
import org.example.taskflow.utils.Session;

import java.time.LocalDate;
import java.util.List;

public class DashboardController {

    @FXML private Text greetingText;

    @FXML private Pane sideBarPane;
    @FXML private Pane homeBtn;
    @FXML private Pane taskBtn;
    @FXML private Button logoutBtn;

    @FXML private Pane progressPane;
    @FXML private Text toDoText;
    @FXML private Text highPrioText;
    @FXML private Text inProgressText;
    @FXML private Text dueTodayText;
    @FXML private Text doneText;
    @FXML private Text completionText;
    @FXML private Text tasksCompletedText;

    @FXML private VBox overdueBox;

    @FXML private ScrollPane trendScrollPane;
    @FXML private Pane trendPane;
    @FXML private Text trendText;

    private final TaskService taskService = new TaskService();
    private final AnalyticsService analyticsService = new AnalyticsService();

    @FXML
    public void initialize() {
        setupActions();
    }

    public void setupActions() {
        CommonUtils.setUpSideBar(homeBtn, taskBtn, logoutBtn);
        greetingText.setText("Hello, " + Session.getUser());
        populateProgression();
        populateTrend();
        populateOverdue();
    }


    public void populateProgression() {
        DashboardStats stats = analyticsService.getStats();

        toDoText.setText(String.valueOf(stats.getTasks()));
        highPrioText.setText(String.valueOf(stats.getHighPriority()));

        inProgressText.setText(String.valueOf(stats.getActive()));
        dueTodayText.setText(String.valueOf(stats.getDueToday()));

        doneText.setText(String.valueOf(stats.getCompleted()));
        completionText.setText((int) stats.getCompletionPercentage() + "%");
    }


    public void populateTrend() {
        DashboardStats stats = analyticsService.getTrend();

        trendText.setText(stats.getWeeklyTrend() + "%");
        tasksCompletedText.setText(String.valueOf(stats.getTasksCompletedThisWeek()));
    }

    public void populateOverdue() {

        TaskLists taskLists = taskService.getTasks(Session.getUserId());
        List<Task> toDoTasks = taskLists.getTodo();
        List<Task> inProgressTasks = taskLists.getInProgress();
        List<Task> doneTasks = taskLists.getDone();

        populateOverdueHelper(toDoTasks, inProgressTasks, doneTasks);
    }


    private void populateOverdueHelper(List<Task>... tasksLists) {
        LocalDate today = LocalDate.now();

        for (int i = 0; i < tasksLists.length; i++) {
            List<Task> currentList = tasksLists[i];

            for (Task task : currentList) {

                if (LocalDate.parse(task.getDueDate()).isBefore(today) && !task.getStatus().equals("DONE")) {
                    HBox row = new HBox();
                    Label taskTitle = new Label(task.getTitle());
                    Label dueDate = new Label(Math.abs(taskService.getDaysRemaining(task.getDueDate())) + " Days Overdue");

                    row.getStyleClass().add("overdue-row");
                    taskTitle.getStyleClass().add("overdue-title");
                    dueDate.getStyleClass().add("overdue-date");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    row.setMaxWidth(315);
                    row.setMinWidth(315);
                    row.getChildren().addAll(taskTitle, spacer, dueDate);

                    overdueBox.getChildren().add(row);
                }
            }
        }
    }
}
