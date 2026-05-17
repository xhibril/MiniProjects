package org.example.trainsys.utils;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.example.trainsys.model.Train;
import org.example.trainsys.services.TrainService;
import java.util.List;

public class TrainSearchUtil {

    public static void setupSearch(
            Button searchBtn,
            Button clearSearchBtn,
            ComboBox<String> depCb,
            ComboBox<String> destCb,
            ObservableList<Train> trainList,
            TableView<Train> trainTableView,
            TrainService trainService
    ) {

        searchBtn.setOnAction(e -> {
            String dep = depCb.getValue();
            String dest = destCb.getValue();

            if (dep == null || dest == null) return;
            trainList.clear();

            List<Train> trains = trainService.getFilteredTrains(dep, dest);

            trainList.addAll(trains);
            trainTableView.setItems(trainList);
            clearSearchBtn.setVisible(true);
        });


        clearSearchBtn.setOnAction(e-> {
            List<Train> trains = trainService.getTrains();

            trainTableView.getItems().clear();
            trainList.clear();

            trainList.addAll(trains);
            trainTableView.setItems(trainList);
            clearSearchBtn.setVisible(false);
        });
    }



    public static void loadDepDestComboBoxes(
            ComboBox<String> depCb,
            ComboBox<String> destCb,
            TrainService trainService
    ) {

        depCb.setPromptText("Departure");
        destCb.setPromptText("Destination");

        depCb.getItems().clear();
        destCb.getItems().clear();

        for (Train t : trainService.getTrains()) {

            if (!depCb.getItems().contains(t.getDep())) {
                depCb.getItems().add(t.getDep());
            }

            if (!destCb.getItems().contains(t.getDest())) {
                destCb.getItems().add(t.getDest());
            }
        }
    }
}
