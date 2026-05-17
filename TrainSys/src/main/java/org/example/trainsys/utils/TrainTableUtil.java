package org.example.trainsys.utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.trainsys.model.Train;

import java.util.List;

public class TrainTableUtil {

    public static void setupTrainTable(
            TableView<Train> table,
            TableColumn<Train, String> nameCol,
            TableColumn<Train, String> depCol,
            TableColumn<Train, String> destCol,
            TableColumn<Train, String> timeCol,
            TableColumn<Train, String> dateCol,
            TableColumn<Train, String> seatsCol,
            ObservableList<Train> list,
            List<Train> data
    ) {

        list.clear();
        list.addAll(data);

        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        depCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDep()));
        destCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDest()));
        timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        dateCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate()));
        seatsCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getSeats())));

        table.setItems(list);
    }
}