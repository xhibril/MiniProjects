package org.example.trainsys.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.example.trainsys.model.Seat;
import org.example.trainsys.model.Train;
import org.example.trainsys.services.StatisticsService;
import org.example.trainsys.services.TrainService;
import org.example.trainsys.utils.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AdminController {

    @FXML private Button addTrainBtn;
    @FXML private TextField trainNameField;
    @FXML private TextField departureField;
    @FXML private TextField destinationField;
    @FXML private TextField departureTimeField;
    @FXML private TextField seatCountField;
    @FXML private DatePicker dateField;

    @FXML private TextField editDepField;
    @FXML private TextField editDestField;
    @FXML private TextField editTimeField;
    @FXML private DatePicker editDateField;
    @FXML private Pane editFieldsPane;
    @FXML private ImageView editModeBtn;
    @FXML private Button saveChangesBtn;

    @FXML private TableView<Train> trainTableView;
    @FXML private TableColumn<Train, String> trainNameCol;
    @FXML private TableColumn<Train, String> depCol;
    @FXML private TableColumn<Train, String> destCol;
    @FXML private TableColumn<Train, String> timeCol;
    @FXML private TableColumn<Train, String> dateCol;
    @FXML private TableColumn<Train, String> seatsCol;

    @FXML private ComboBox<String> depCb;
    @FXML private ComboBox<String> destCb;
    @FXML private Button searchBtn;
    @FXML private Button clearSearchBtn;

    @FXML private ScrollPane seatsPanel;
    @FXML private Pane seatsPane;
    @FXML private Label seatDetailsLabel;
    @FXML private Text noSeatsLabel;

    @FXML private BarChart<String, Number> chart;
    @FXML private Pane chartPane;
    @FXML private DatePicker fromDateRevenue;
    @FXML private DatePicker toDateRevenue;
    @FXML private Text revenueLabel;

    @FXML private ImageView deleteBtn;
    @FXML private Button logoutBtn;
    @FXML private Label notifLabel;
    @FXML private Pane trainWrapper;
    @FXML private Pane fieldsPane;


    private Train selectedTrain = null;
    private final TrainService trainService = new TrainService();
    private final StatisticsService statisticsService = new StatisticsService();

    ObservableList<Train> trainList = FXCollections.observableArrayList();

    private LocalDate fromDate;
    private LocalDate toDate;

    @FXML
    public void initialize() {
        notifLabel.setVisible(false);
        setupActions();
        initLayout();
    }

    public void setTrainTableView() {

        TrainTableUtil.setupTrainTable(trainTableView, trainNameCol, depCol, destCol, timeCol, dateCol, seatsCol, trainList, trainService.getTrains());
        TrainSearchUtil.setupSearch(searchBtn, clearSearchBtn, depCb, destCb, trainList, trainTableView, trainService);
        TrainSearchUtil.loadDepDestComboBoxes(depCb, destCb, trainService);

        trainTableView.setOnMouseClicked(e -> {
            Train selected = trainTableView.getSelectionModel().getSelectedItem();

            if (selected == null) {
                selectedTrain = null;
                trainTableView.getSelectionModel().clearSelection();
                clearFields();
                noSelectedTrainLayout();
                initLayout();
                return;
            }

            if (selectedTrain == selected) {
                selectedTrain = null;
                trainTableView.getSelectionModel().clearSelection();
                noSelectedTrainLayout();
                clearFields();
                initLayout();
            } else {
                selectedTrain = selected;
                seatDetailsLabel.setVisible(false);
                renderSeatsPanel();
                selectedTrainLayout();
            }
        });
    }

    public void setChart() {
        chart.setLegendVisible(false);

        Map<String, Integer> ticketsSoldPerTrain = statisticsService.getTicketsSoldPerTrain();
        chart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<String, Integer> entry : ticketsSoldPerTrain.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        chart.getData().add(series);
    }

    public void setRevenue() {
        fromDateRevenue.setOnAction(e -> {
            fromDate = fromDateRevenue.getValue();
            getRevenueData();
        });

        toDateRevenue.setOnAction(e -> {
            toDate = toDateRevenue.getValue();
            getRevenueData();
        });
    }

    public void getRevenueData() {
        if (fromDate != null && toDate != null) {
            Double revenue = statisticsService.getRevenue(fromDate, toDate);
            revenueLabel.setText(revenue + "$");
        }
    }


    public void setEditModeBtn() {
        editModeBtn.setOnMouseClicked(e -> {
            if (editFieldsPane.isVisible()) {
                editingModeOffLayout();
            } else {
                editingModeOnLayout();
                fillFields();
            }
        });
    }

    public void setSaveChangesBtn() {
        saveChangesBtn.setOnAction(e -> {

            if (selectedTrain == null) {
                Notifier.showError(notifLabel, "You must select a train");
                return;
            }

            if (CommonUtils.areEmpty(editDepField, editDestField, editTimeField)) {
                Notifier.showError(notifLabel, "Fill all edit fields");
                return;
            }

            if (editDateField.getValue() == null) {
                Notifier.showError(notifLabel, "Select a date");
                return;
            }

            Train train = new Train();
            train.setId(selectedTrain.getId());
            train.setDep(editDepField.getText());
            train.setDest(editDestField.getText());
            train.setTime(editTimeField.getText());
            train.setDate( editDateField.getValue().toString());

            boolean isSuccess = trainService.updateTrainDetails(train);

            if (isSuccess) {
                Notifier.showSuccess(notifLabel, "Train details successfully updated");
                clearFields();

                trainList.clear();
                trainList.addAll(trainService.getTrains());
            } else {
                Notifier.showError(notifLabel, "Something went wrong, try again");
            }
        });
    }

    public void setDeleteBtn() {
        deleteBtn.setOnMouseClicked(e -> {

            if (selectedTrain == null) {
                Notifier.showError(notifLabel, "Select a train first");
                return;
            }

            if (selectedTrain.getId() != null) {
                boolean isSuccess = trainService.deleteTrain(selectedTrain.getId());

                if (isSuccess) {
                    Notifier.showSuccess(notifLabel, "Train successfully deleted");
                    trainList.remove(selectedTrain);
                    selectedTrain = null;

                    clearFields();
                    initLayout();
                    setChart();
                    getRevenueData();

                } else {
                    Notifier.showError(notifLabel, "Something went wrong, please try again");
                }
            }
        });
    }

    public void setAddTrainBtn() {
        addTrainBtn.setOnAction(e -> {
            if (CommonUtils.areEmpty(trainNameField, departureField, destinationField, departureTimeField, seatCountField)) {
                Notifier.showError(notifLabel, "Fill all fields");
                return;
            }

            if (dateField.getValue() == null) {
                Notifier.showError(notifLabel, "Select a date");
                return;
            }

            int seatCount;
            try {
                seatCount = Integer.parseInt(seatCountField.getText());
            } catch (Exception ex) {
                Notifier.showError(notifLabel, "Seat count must be a number");
                return;
            }

            boolean doesTrainExist = trainService.doesTrainExist(trainNameField.getText().trim());

            if(doesTrainExist){
                Notifier.showError(notifLabel, "Train name already exists");
                return;
            }

            Train train = new Train();
            train.setName(trainNameField.getText().trim());
            train.setDep(departureField.getText());
            train.setDest(destinationField.getText());
            train.setTime(departureTimeField.getText());
            train.setDate(dateField.getValue().toString());
            train.setSeats(seatCount);

            boolean isSuccess = trainService.createTrain(train);

            if (isSuccess) {
                Notifier.showSuccess(notifLabel, "Train successfully created");

                trainList.clear();
                trainList.addAll(trainService.getTrains());
                clearFields();

                TrainSearchUtil.setupSearch(searchBtn, clearSearchBtn, depCb, destCb, trainList, trainTableView, trainService);
                TrainSearchUtil.loadDepDestComboBoxes(depCb, destCb, trainService);
            } else {
                Notifier.showError(notifLabel, "Could not create train, try again");
            }
        });
    }


    public void renderSeatsPanel() {
        List<Seat> seatList = trainService.getSeats(selectedTrain.getId());
        if (seatList != null) {
            Pane pane = new Pane();

            int cols = 5;
            int spacing = 90;

            for (int i = 0; i < seatList.size(); i++) {
                Seat seat = seatList.get(i);

                Button seatBtn = createSeatButton(seat);

                int row = i / cols;
                int col = i % cols;

                seatBtn.setLayoutX(col * spacing);
                seatBtn.setLayoutY(row * spacing);
                pane.getChildren().add(seatBtn);
            }
            seatsPanel.setContent(pane);
        } else {
            Notifier.showError(notifLabel, "Could not render train seats, try again");
        }
    }

    private Button createSeatButton(Seat seat) {
        Button seatBtn = new Button(String.valueOf(seat.getSeatNumber()));

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/org/example/trainsys/images/seat.png")));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        seatBtn.setPrefSize(70, 35);
        seatBtn.setGraphic(icon);

        if (seat.isBooked()) {
            seatBtn.setStyle("-fx-background-color: #EF4444;");
        } else {
            seatBtn.setStyle("-fx-background-color: #22C55E;");
        }

        seatBtn.setOnAction(e -> {
            Seat seatDetails = trainService.getSeatDetails(selectedTrain.getId(), seat.getSeatNumber());

            if (seatDetails.getBookedBy() != null) {
                seatDetailsLabel.setVisible(true);
                seatDetailsLabel.setText(seatDetails.getBookedBy() + " | " + seatDetails.getEmail());
            } else {
                seatDetailsLabel.setVisible(false);
            }
        });
        return seatBtn;
    }

    private void setupActions() {
        setTrainTableView();
        setSaveChangesBtn();
        setDeleteBtn();
        setAddTrainBtn();
        setEditModeBtn();
        setChart();
        setRevenue();

        CommonUtils.setUpLogout(logoutBtn);
    }

    public void clearFields() {
        departureField.setText("");
        destinationField.setText("");
        departureTimeField.setText("");
        seatCountField.setText("");
        trainNameField.setText("");
        dateField.setValue(null);

        editDepField.setText("");
        editDestField.setText("");
        editTimeField.setText("");
        editDateField.setValue(null);
    }

    public void fillFields() {
        editDepField.setText(selectedTrain.getDep());
        editDestField.setText(selectedTrain.getDest());
        editTimeField.setText(selectedTrain.getTime());
        editDateField.setValue(LocalDate.parse(selectedTrain.getDate()));
    }

    public void initLayout() {
        editModeBtn.setManaged(false);
        editModeBtn.setVisible(false);
        deleteBtn.setManaged(false);
        deleteBtn.setVisible(false);
        editFieldsPane.setManaged(false);
        editFieldsPane.setVisible(false);
        seatDetailsLabel.setVisible(false);
        seatsPanel.setVisible(false);
        trainWrapper.setPrefHeight(315);
        clearSearchBtn.setVisible(false);
    }

    public void selectedTrainLayout() {
        editModeBtn.setManaged(true);
        editModeBtn.setVisible(true);
        deleteBtn.setManaged(true);
        deleteBtn.setVisible(true);
        seatsPanel.setVisible(true);
        noSeatsLabel.setVisible(false);
        trainWrapper.setPrefHeight(335);
        chartPane.setLayoutY(418);
    }

    public void noSelectedTrainLayout() {
        seatsPanel.setVisible(false);
        noSeatsLabel.setVisible(true);
    }

    public void editingModeOnLayout() {
        trainWrapper.setPrefHeight(400);
        editFieldsPane.setVisible(true);
        chartPane.setLayoutY(470);
    }

    public void editingModeOffLayout() {
        trainWrapper.setPrefHeight(340);
        editFieldsPane.setVisible(false);
        chartPane.setLayoutY(418);
    }
}