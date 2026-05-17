package org.example.trainsys.controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.trainsys.dto.ApiResponse;
import org.example.trainsys.model.Seat;
import org.example.trainsys.model.Ticket;
import org.example.trainsys.model.Train;
import org.example.trainsys.services.TicketService;
import org.example.trainsys.services.TrainService;
import org.example.trainsys.utils.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    @FXML private Pane paymentPanel;
    @FXML private Pane cardPaymentPane;
    @FXML private Button completePaymentBtn;
    @FXML private Button cardPaymentBtn;
    @FXML private Button cashPaymentBtn;
    @FXML private TextField cardNumField;
    @FXML private TextField cardCvvField;
    @FXML private Label finalPriceLabel;
    @FXML private Text paymentText;


    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private RadioButton standardClassBtn;
    @FXML private RadioButton vipClassBtn;

    @FXML private ComboBox<String> depCb;
    @FXML private ComboBox<String> destCb;
    @FXML private Button searchBtn;
    @FXML private Button clearSearchBtn;

    @FXML private TableView<Train> trainTableView;
    @FXML private TableColumn<Train, String> trainNameCol;
    @FXML private TableColumn<Train, String> trainDepCol;
    @FXML private TableColumn<Train, String> trainDestCol;
    @FXML private TableColumn<Train, String> trainTimeCol;
    @FXML private TableColumn<Train, String> trainDateCol;
    @FXML private TableColumn<Train, String> trainSeatsCol;


    @FXML private ScrollPane seatsPanel;
    @FXML private Text noSeatsLabel;


    @FXML private TableView<Ticket> ticketTableView;
    @FXML private TableColumn<Ticket, String> ticketNameCol;
    @FXML private TableColumn<Ticket, String> ticketDetailsCol;
    @FXML private Pane ticketsWrapper;
    @FXML private ImageView cancelTicketBtn;
    @FXML private ImageView downloadTicketsBtn;


    @FXML private Label notifLabel;
    @FXML private Button logoutBtn;


    private Ticket selectedTicket;
    private Train selectedTrain;
    private String selectedClass;
    private Double finalPrice;

    private final ObservableList<Train> trainList = FXCollections.observableArrayList();
    private final ObservableList<Ticket> ticketList = FXCollections.observableArrayList();
    private final List<Seat> seatsPicked = new ArrayList<>();

    private final TrainService trainService = new TrainService();
    private final TicketService ticketService = new TicketService();


    @FXML
    public void initialize(){
        notifLabel.setVisible(false);
        initLayout();
        setupActions();
    }

    public void setTrainTableView(){
        TrainTableUtil.setupTrainTable(trainTableView, trainNameCol, trainDepCol, trainDestCol,
                trainTimeCol, trainDateCol, trainSeatsCol, trainList, trainService.getTrains());

        trainTableView.setOnMouseClicked(e -> {
            Train selected = trainTableView.getSelectionModel().getSelectedItem();
            seatsPicked.clear();
            clearFields();
            finalPriceLabel.setVisible(false);

            if (selected == null) {
                selectedTrain = null;
                trainTableView.getSelectionModel().clearSelection();
                seatsPanel.setVisible(false);
                paymentPanel.setVisible(false);
                noSelectedTrainLayout();
                return;
            }

            if (selectedTrain == selected) {
                selectedTrain = null;
                trainTableView.getSelectionModel().clearSelection();
                seatsPanel.setVisible(false);
                noSelectedTrainLayout();
                paymentPanel.setVisible(false);
            } else {
                selectedTrain = selected;
                renderSeatsPanel();
                seatsPanel.setVisible(true);
                selectedTrainLayout();
            }
        });
    }

    public void setTicketTableView() {
        ticketList.clear();
        ticketList.addAll(ticketService.getTickets(Session.getUserId()));

        ticketNameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        ticketDetailsCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetails()));

        ticketTableView.setItems(ticketList);

        ticketTableView.setOnMouseClicked(e -> {
            Ticket selected = ticketTableView.getSelectionModel().getSelectedItem();

            if (selected == null) {
                selectedTicket = null;
                ticketTableView.getSelectionModel().clearSelection();
                ticketNotSelectedLayout();
                return;
            }

            if (selectedTicket == selected) {
                selectedTicket = null;
                ticketTableView.getSelectionModel().clearSelection();
                ticketNotSelectedLayout();
            } else {
                selectedTicket = selected;
                ticketSelectedLayout();
            }
        });
    }

    public void setupActions(){
        setTrainTableView();
        setCardPaymentBtn();
        setCashPaymentBtn();
        setClassBtns();
        setCompletePaymentBtn();
        setTicketTableView();
        setCancelTicketBtn();
        setDownloadTicketBtn();
        initLayout();

        TrainSearchUtil.setupSearch(searchBtn, clearSearchBtn, depCb, destCb, trainList, trainTableView, trainService);
        TrainSearchUtil.loadDepDestComboBoxes(depCb, destCb, trainService);
        CommonUtils.setUpLogout(logoutBtn);
    }


    public void setCardPaymentBtn() {
        cardPaymentBtn.setOnAction(e -> {
            if (cardPaymentPane.isVisible()) {
                cardPaymentPane.setVisible(false);
                cardPaymentOffLayout();
            } else {
                cardPaymentPane.setVisible(true);
                cardPaymentOnLayout();
            }
        });
    }

    public void setCashPaymentBtn() {
        cashPaymentBtn.setOnAction(e -> cardPaymentOffLayout());
    }

    public void setCompletePaymentBtn() {
        completePaymentBtn.setOnAction(e -> {

            if (CommonUtils.areEmpty(nameField, emailField)) {
                Notifier.showError(notifLabel, "Fill all required fields");
                return;
            }

            if (selectedTrain == null) {
                Notifier.showError(notifLabel, "Select a train first");
                return;
            }

            if (seatsPicked.isEmpty()) {
                Notifier.showError(notifLabel, "Select at least one seat");
                return;
            }

            String name = nameField.getText();
            String email = emailField.getText();

            if (standardClassBtn.isSelected()) {
                selectedClass = "STANDARD";
            } else if (vipClassBtn.isSelected()) {
                selectedClass = "VIP";
            } else {
                Notifier.showError(notifLabel, "Select a class first");
                return;
            }

            LocalDate date = LocalDate.parse(selectedTrain.getDate());
            LocalTime time = LocalTime.parse(selectedTrain.getTime());
            LocalDateTime departureTime = LocalDateTime.of(date, time);

            String seats = ticketService.getTicketSeats(seatsPicked);

            String trainDetails = selectedTrain.getDep() + " -> " + selectedTrain.getDest() + " | " +
                    selectedTrain.getDate() + " | " + selectedTrain.getTime() + " | " + seats;

            Ticket ticket = new Ticket();
            ticket.setUserId(Session.getUserId());
            ticket.setTrainId(selectedTrain.getId());
            ticket.setEmail(email);
            ticket.setName(name);
            ticket.setDetails(trainDetails);
            ticket.setDepTime(departureTime);
            ticket.setBookingDate(LocalDateTime.now().toString());
            ticket.setPrice(finalPrice);

            ApiResponse res = ticketService.bookTicket(ticket, seatsPicked);

            if (res.isSuccess()) {
                Notifier.showSuccess(notifLabel, res.getMessage());
                clearFields();
                renderSeatsPanel();
                setTicketTableView();
                finalPriceLabel.setVisible(false);
                seatsPicked.clear();
                cardPaymentOffLayout();
            } else {
                Notifier.showError(notifLabel, res.getMessage());
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
            Notifier.showError(notifLabel, "Could not retrieve train seats, try again");
        }
    }

    public Button createSeatButton(Seat seat) {
        Button seatBtn = new Button(String.valueOf(seat.getSeatNumber()));

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/org/example/trainsys/images/seat.png")));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        seatBtn.setPrefSize(70, 35);
        seatBtn.setGraphic(icon);

        if (seat.getUserId() == Session.getUserId()) {
            seatBtn.setStyle("-fx-background-color: #dec75e;");
        } else if (seat.isBooked()) {
            seatBtn.setStyle("-fx-background-color: #EF4444;");
        } else {
            seatBtn.setStyle("-fx-background-color: #22C55E;");
        }

        seatBtn.setOnAction(e -> {
            Seat seatDetails = trainService.getSeatDetails(selectedTrain.getId(), seat.getSeatNumber());

            if (!seatDetails.isBooked()) {
                Seat existing = null;

                for (Seat currentSeat : seatsPicked) {
                    if (currentSeat.getSeatNumber() == seat.getSeatNumber()) {
                        existing = currentSeat;
                        break;
                    }
                }

                if (existing != null) {
                    seatsPicked.remove(existing);
                    seatBtn.setStyle("-fx-background-color: #22C55E;");
                } else {
                    Seat newSeat = new Seat();
                    newSeat.setSeatNumber(seat.getSeatNumber());
                    seatsPicked.add(newSeat);
                    seatBtn.setStyle("-fx-background-color: #7e7b7b;");
                }
                updateFinalPrice();
            }

            if (seatsPicked.isEmpty()) {
                paymentOffLayout();
            } else {
                paymentOnLayout();
            }
        });

        return seatBtn;
    }


    public void updateFinalPrice() {
        if (selectedClass == null) {
            finalPriceLabel.setVisible(false);
            return;
        }
        double price = 0;

        for (int i = 0; i < seatsPicked.size(); i++) {
            price = ticketService.calculateTicketPrice(price, selectedClass, "ADD");
        }

        finalPrice = price;
        finalPriceLabel.setVisible(true);
        finalPriceLabel.setText("To Pay: " + finalPrice + "€");
    }

    public void setClassBtns() {
        standardClassBtn.setOnAction(e -> {
            if(vipClassBtn.isSelected()) vipClassBtn.setSelected(false);

            if(standardClassBtn.isSelected()){
                selectedClass = "STANDARD";
            } else {
                selectedClass = null;
            }
            updateFinalPrice();
        });

        vipClassBtn.setOnAction(e -> {
            if(standardClassBtn.isSelected()) standardClassBtn.setSelected(false);

            if(vipClassBtn.isSelected()){
                selectedClass = "VIP";
            } else {
                selectedClass = null;
            }
            updateFinalPrice();
        });
    }

    public void clearFields(){
        nameField.setText("");
        emailField.setText("");
        standardClassBtn.setSelected(false);
        vipClassBtn.setSelected(false);
        cardNumField.setText("");
        cardCvvField.setText("");
    }

    public void setCancelTicketBtn(){
        cancelTicketBtn.setOnMouseClicked(e->{

            Ticket ticket = ticketService.getTicketDepartureTime(selectedTicket.getId());
            LocalDateTime departure = ticket.getDepTime();

            if(selectedTicket != null) {
                if (departure.isAfter(LocalDateTime.now().plusHours(3))) {

                    if (ticketService.cancelTicket(selectedTicket.getId())) {
                        Notifier.showSuccess(notifLabel, "Ticket successfully canceled");
                        ticketList.remove(selectedTicket);
                        ticketTableView.refresh();
                        noSelectedTrainLayout();
                    } else {
                        Notifier.showError(notifLabel, "Could not delete ticket, please try again");
                    }

                } else {
                    Notifier.showError(notifLabel, "Cannot cancel less than 3 hours before departure");
                }
            } else {
                Notifier.showError(notifLabel, "You must selected a ticket");
            }
        });
    }

    public void setDownloadTicketBtn() {
        downloadTicketsBtn.setOnMouseClicked(e -> {

            if (selectedTicket == null) {
                Notifier.showError(notifLabel, "You must select a ticket");
                return;
            }

            try {

                String content =
                        "----- TRAIN TICKET -----\n" +
                                "Name: " + selectedTicket.getName() + "\n" +
                                "Email: " + selectedTicket.getEmail() + "\n" +
                                "Details: " + selectedTicket.getDetails() + "\n" +
                                "Price: " + selectedTicket.getPrice() + "€\n" +
                                "------------------------";

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Ticket");
                fileChooser.setInitialFileName("ticket_" + selectedTicket.getId() + ".txt");
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt")
                );

                File file = fileChooser.showSaveDialog(downloadTicketsBtn.getScene().getWindow());

                if (file != null) {
                    Files.write(file.toPath(), content.getBytes());
                    Notifier.showSuccess(notifLabel, "Ticket downloaded successfully");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Notifier.showError(notifLabel, "Failed to download ticket");
            }
        });
    }

    public void initLayout(){
        ticketsWrapper.setPrefHeight(285);
        completePaymentBtn.setLayoutY(240);
        finalPriceLabel.setLayoutY(195);
        paymentPanel.setPrefHeight(286);

        cardPaymentPane.setVisible(false);
        cardPaymentPane.setManaged(false);

        cancelTicketBtn.setVisible(false);
        downloadTicketsBtn.setVisible(false);
        clearSearchBtn.setVisible(false);
        paymentText.setVisible(false);
        paymentPanel.setVisible(false);
    }

    public void ticketSelectedLayout(){
        ticketsWrapper.setPrefHeight(320);
        cancelTicketBtn.setVisible(true);
        downloadTicketsBtn.setVisible(true);
    }

    public void ticketNotSelectedLayout(){
        ticketsWrapper.setPrefHeight(285);
        cancelTicketBtn.setVisible(false);
        downloadTicketsBtn.setVisible(false);
    }

    public void selectedTrainLayout(){
        seatsPanel.setVisible(true);
        noSeatsLabel.setVisible(false);
        paymentText.setVisible(false);
        paymentPanel.setVisible(false);
    }

    public void noSelectedTrainLayout(){
        seatsPanel.setVisible(false);
        noSeatsLabel.setVisible(true);
        paymentText.setVisible(false);
        paymentPanel.setVisible(false);
    }

    public void paymentOnLayout(){
        paymentPanel.setVisible(true);
        paymentText.setVisible(true);
    }

    public void paymentOffLayout(){
        paymentPanel.setVisible(false);
        cardPaymentOffLayout();
        paymentText.setVisible(false);

        selectedClass = null;
        finalPrice = 0.0;

        standardClassBtn.setSelected(false);
        vipClassBtn.setSelected(false);
        updateFinalPrice();
    }

    public void cardPaymentOnLayout(){
        completePaymentBtn.setLayoutY(310);
        finalPriceLabel.setLayoutY(280);
        paymentPanel.setPrefHeight(350);
    }

    public void cardPaymentOffLayout(){
        completePaymentBtn.setLayoutY(240);
        finalPriceLabel.setLayoutY(195);
        cardPaymentPane.setVisible(false);
        paymentPanel.setPrefHeight(286);
    }
}