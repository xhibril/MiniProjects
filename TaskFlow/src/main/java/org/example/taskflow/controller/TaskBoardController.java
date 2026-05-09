package org.example.taskflow.controller;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.taskflow.dto.ApiResponse;
import org.example.taskflow.dto.TaskLists;
import org.example.taskflow.model.Task;
import org.example.taskflow.services.TaskService;
import org.example.taskflow.utils.CommonUtils;
import org.example.taskflow.utils.Notifier;
import org.example.taskflow.utils.Session;

import java.time.LocalDate;
import java.util.List;

public class TaskBoardController {

    @FXML private Text greetingText;
    @FXML private ImageView profile;

    @FXML private Pane sideBarPane;
    @FXML private Pane homeBtn;
    @FXML private Pane taskBtn;
    @FXML private Button logoutBtn;

    @FXML private TextField searchField;
    @FXML private ImageView searchBtn;
    @FXML private Button clearBtn;

    @FXML private VBox toDoBox;
    @FXML private VBox inProgressBox;
    @FXML private VBox doneBox;

    @FXML private Button toDoAdd;
    @FXML private Button inProgressAdd;
    @FXML private Button doneAdd;

    @FXML private Pane taskFieldsPane;
    @FXML private TextField taskNameField;
    @FXML private TextArea taskDescField;
    @FXML private DatePicker taskDueDate;
    @FXML private RadioButton lowPrio;
    @FXML private RadioButton mediumPrio;
    @FXML private RadioButton highPrio;
    @FXML private Button saveTaskBtn;
    @FXML private Button addTask;

    @FXML private HBox taskOptionsBox;
    @FXML private Label notifLabel;

    private boolean isAddingTask = false;
    private String currentStatus;
    private ToggleGroup priorityGroup;
    private Task editingTask;

    private final TaskService taskService = new TaskService();

    private enum Mode {
        CREATE, EDIT
    }

    private Mode currentMode = Mode.CREATE;

    @FXML
    public void initialize() {
        setupActions();

    }

    public void setupActions() {
        setAddBtns();
        initLayout();
        setSaveTaskBtn();
        populateColumns();
        setSearchBtn();
        setClearBtn();

        priorityGroup = new ToggleGroup();

        lowPrio.setToggleGroup(priorityGroup);
        mediumPrio.setToggleGroup(priorityGroup);
        highPrio.setToggleGroup(priorityGroup);

        CommonUtils.setUpSideBar(homeBtn, taskBtn, logoutBtn);
    }

    public void setAddBtns() {
        addBtnsHelper(addTask, toDoBox, "TODO");
        addBtnsHelper(toDoAdd, toDoBox, "TODO");
        addBtnsHelper(inProgressAdd, inProgressBox, "IN_PROGRESS");
        addBtnsHelper(doneAdd, doneBox, "DONE");
    }

    public void addBtnsHelper(Button addBtn, VBox box, String status) {
        addBtn.setOnAction(e -> {
            currentMode = Mode.CREATE;
            if (isAddingTask) {
                addingTaskLayoutOff();
                isAddingTask = false;
            } else {
                Parent oldParent = taskFieldsPane.getParent();

                if (oldParent instanceof Pane p) {
                    p.getChildren().remove(taskFieldsPane);
                }

                currentStatus = status;

                taskFieldsPane.setMinHeight(378);
                taskFieldsPane.setPrefHeight(378);

                box.getChildren().add(taskFieldsPane);
                addingTaskLayout();
                isAddingTask = true;
            }
        });
    }

    public void setSearchBtn() {
        searchBtn.setOnMouseClicked(e -> {
            TaskLists taskLists = taskService.searchTask(Session.getUserId(), searchField.getText());
            populateColumnsHelper(taskLists);
            toggleNode(clearBtn, true);
        });
    }


    public void setClearBtn() {
        clearBtn.setOnAction(e -> {
            populateColumns();
            toggleNode(clearBtn, false);
            searchField.setText("");
        });
    }


    public void setSaveTaskBtn() {
        saveTaskBtn.setOnAction(e -> {
            if (CommonUtils.areEmpty(taskNameField, taskDescField)) {
                Notifier.show(notifLabel, "Fill all fields", "ERROR");
                return;
            }

            if (taskDueDate.getValue() == null || priorityGroup.getSelectedToggle() == null) {
                Notifier.show(notifLabel, "Fill all fields", "ERROR");
                return;
            }

            Toggle selected = priorityGroup.getSelectedToggle();
            Task task = new Task();
            task.setTitle(taskNameField.getText());
            task.setDescription(taskDescField.getText());
            task.setStatus(currentStatus);
            task.setDueDate(taskDueDate.getValue().toString());
            task.setCreatedAt(LocalDate.now().toString());

            if (selected == lowPrio) {
                task.setPriority("LOW");
            } else if (selected == mediumPrio) {
                task.setPriority("MEDIUM");
            } else {
                task.setPriority("HIGH");
            }

            ApiResponse res;

            if (currentMode == Mode.CREATE) {
                res = taskService.addTask(Session.getUserId(), task);
            } else {
                task.setId(editingTask.getId());
                res = taskService.editTask(task);
            }

            if (res.isSuccess()) {
                Notifier.showSuccess(notifLabel, res.getMessage());
                clearFields();
                addingTaskLayoutOff();
                populateColumns();
                isAddingTask = false;
            } else {
                Notifier.showError(notifLabel, res.getMessage());
            }
        });
    }


    public void populateColumns() {
        TaskLists taskLists = taskService.getTasks(Session.getUserId());

        if (taskLists == null) {
            Notifier.showError(notifLabel, "Could not retrieve tasks, please try again");
            return;
        }

        populateColumnsHelper(taskLists);
    }


    public void populateColumnsHelper(TaskLists taskLists) {
        toDoBox.getChildren().clear();
        inProgressBox.getChildren().clear();
        doneBox.getChildren().clear();

        populateTaskList(taskLists.getTodo(), toDoBox);
        populateTaskList(taskLists.getInProgress(), inProgressBox);
        populateTaskList(taskLists.getDone(), doneBox);
    }


    public VBox taskCard(Task task) {
        VBox card = new VBox();

        HBox topRow = new HBox();

        Label priority = new Label(task.getPriority());
        setPrioStyle(priority, task);

        Label daysRemaining = new Label("");
        setDaysRemainingLabel(daysRemaining, task);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.getChildren().addAll(priority, spacer, daysRemaining);

        Label title = new Label(task.getTitle());
        Label desc = new Label(task.getDescription());
        setUpTaskText(title, desc);

        Button progressBtn = createProgressBtn(task);
        setProgressionBtns(progressBtn, task);

        card.getChildren().addAll(topRow, title, desc, progressBtn);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");

        card.setOnMouseClicked(e -> {
            if (taskOptionsBox.isVisible()) {
                taskSelectedOffLayout();
            } else {
                removeFromParent(taskOptionsBox);

                VBox parentBox = (VBox) card.getParent();

                int index = parentBox.getChildren().indexOf(card);

                Button deleteBtn = new Button("Delete");
                Button editBtn = new Button("Edit");

                deleteBtn.getStyleClass().add("delete-logout-btn");
                editBtn.getStyleClass().add("edit-btn");

                setTaskOptionsBtns(editBtn, deleteBtn, task, card);
                taskSelectedLayout();

                taskOptionsBox.getChildren().clear();
                taskOptionsBox.getChildren().addAll(deleteBtn, editBtn);

                parentBox.getChildren().add(index + 1, taskOptionsBox);
            }
        });
        return card;
    }



    public void setTaskOptionsBtns(Button editBtn, Button deleteBtn, Task task, VBox card) {
        editBtn.setOnAction(e -> {
            if (taskFieldsPane.isVisible()) {
                toggleNode(taskFieldsPane, false);
            } else {
                currentMode = Mode.EDIT;
                editingTask = task;
                currentStatus = task.getStatus();

                taskNameField.setText(task.getTitle());
                taskDescField.setText(task.getDescription());
                taskDueDate.setValue(LocalDate.parse(task.getDueDate()));

                switch (task.getPriority()) {
                    case "LOW" -> priorityGroup.selectToggle(lowPrio);
                    case "MEDIUM" -> priorityGroup.selectToggle(mediumPrio);
                    case "HIGH" -> priorityGroup.selectToggle(highPrio);
                }

                VBox parentBox = (VBox) editBtn.getParent().getParent();

                removeFromParent(taskFieldsPane);

                setupTaskPaneSize();

                int index = parentBox.getChildren().indexOf(card);
                parentBox.getChildren().add(index + 2, taskFieldsPane);

                toggleNode(taskFieldsPane, true);
            }
        });

        deleteBtn.setOnAction(e -> {
            ApiResponse res = taskService.deleteTask(task.getId());

            if (res.isSuccess()) {
                Notifier.showSuccess(notifLabel, res.getMessage());
            } else {
                Notifier.showError(notifLabel, res.getMessage());
            }
            initLayout();
            populateColumns();
        });
    }


    public void setProgressionBtns(Button progressBtn, Task task) {
        progressBtn.getStyleClass().removeAll("progress-btn-start", "progress-btn-complete", "progress-btn-reopen");

        switch (task.getStatus()) {
            case "TODO" -> progressBtn.getStyleClass().add("progress-btn-start");
            case "IN_PROGRESS" -> progressBtn.getStyleClass().add("progress-btn-complete");
            case "DONE" -> progressBtn.getStyleClass().add("progress-btn-reopen");
        }

        progressBtn.setOnAction(e -> {
            switch (task.getStatus()) {
                case "TODO" -> task.setStatus("IN_PROGRESS");

                case "IN_PROGRESS" -> {
                    task.setStatus("DONE");
                    task.setCompletedAt(LocalDate.now().toString());
                }

                case "DONE" -> {
                    task.setStatus("TODO");
                    task.setCompletedAt(null);
                }
            }
            taskService.editTask(task);
            populateColumns();
        });
    }


    public void setPrioStyle(Label priority, Task task) {
        switch(task.getPriority()){
            case "LOW" -> priority.getStyleClass().add("prio-low");
            case "MEDIUM" -> priority.getStyleClass().add("prio-medium");
            case "HIGH" -> priority.getStyleClass().add("prio-high");
        }
    }

    public void setDaysRemainingLabel(Label daysRemainingLabel, Task task) {
        long daysRemaining = Math.abs(taskService.getDaysRemaining(task.getDueDate()));

        if (LocalDate.parse(task.getDueDate()).isBefore(LocalDate.now()) && !task.getStatus().equals("DONE")) {
            daysRemainingLabel.getStyleClass().add("days-remaining-overdue");
            daysRemainingLabel.setText(daysRemaining + " Days Overdue");

        } else if (task.getStatus().equals("DONE")) {
            daysRemainingLabel.setText("");

        } else {
            daysRemainingLabel.getStyleClass().add("days-remaining");
            daysRemainingLabel.setText(Math.abs(daysRemaining) + " Days");
        }
    }

    public Button createProgressBtn(Task task){
        return switch(task.getStatus()){
            case "TODO" -> new Button("Start");
            case "IN_PROGRESS" -> new Button("Complete");
            case "DONE" -> new Button("Reopen");
            default -> new Button();
        };
    }


    public void initLayout() {
        toggleNode(taskFieldsPane, false);
        toggleNode(taskOptionsBox, false);
        toggleNode(clearBtn, false);
    }

    public void clearFields() {
        taskNameField.setText("");
        taskDescField.setText("");
        priorityGroup.selectToggle(null);
        taskDueDate.setValue(null);
    }

    public void addingTaskLayout() {
        clearFields();
        toggleNode(taskFieldsPane, true);
        toggleNode(taskOptionsBox, false);
    }

    public void addingTaskLayoutOff() {
        toggleNode(taskFieldsPane, false);
        toggleNode(taskOptionsBox, false);
    }


    public void taskSelectedLayout() {
        toggleNode(taskOptionsBox, true);
        toggleNode(taskFieldsPane, false);
    }

    public void taskSelectedOffLayout() {
        toggleNode(taskFieldsPane, false);
        toggleNode(taskOptionsBox, false);
    }

    public void setupTaskPaneSize(){
        taskFieldsPane.setMinHeight(378);
        taskFieldsPane.setPrefHeight(378);
    }

    public void removeFromParent(Node node){
        Parent parent = node.getParent();

        if(parent instanceof Pane p){
            p.getChildren().remove(node);
        }
    }

    public void toggleNode(Region node, boolean state){
        node.setVisible(state);
        node.setManaged(state);
    }

    public void populateTaskList(List<Task> tasks, VBox box){
        for(Task task : tasks){
            box.getChildren().add(taskCard(task));
        }
    }


    public void setUpTaskText(Label title, Label desc){
        title.getStyleClass().add("task-title");
        desc.getStyleClass().add("task-desc");

        desc.setWrapText(true);
        title.setWrapText(true);

        desc.setMinHeight(Region.USE_PREF_SIZE);
        title.setMinHeight(Region.USE_PREF_SIZE);
    }
}
