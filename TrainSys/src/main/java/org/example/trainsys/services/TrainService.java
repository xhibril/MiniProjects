package org.example.trainsys.services;
import org.example.trainsys.dao.TrainDAO;
import org.example.trainsys.model.Seat;
import org.example.trainsys.model.Train;
import java.util.List;

public class TrainService {

    private final TrainDAO trainDAO = new TrainDAO();

    public List<Train> getTrains(){
        return trainDAO.getTrains();
    }

    public List<Train> getFilteredTrains(String dep, String dest){
        return trainDAO.getFilteredTrains(dep, dest);
    }

    public boolean updateTrainDetails(Train train){
        return trainDAO.updateTrainDetails(train);
    }


    public boolean deleteTrain(Long id){
        return trainDAO.deleteTrain(id);
    }


    public boolean createTrain(Train train){
        return trainDAO.createTrain(train);
    }

    public List<Seat> getSeats(Long trainId){
        return trainDAO.getSeats(trainId);
    }

    public Seat getSeatDetails(Long trainId, int seatNumber){
        return trainDAO.getSeatDetails(seatNumber, trainId);
    }

    public boolean doesTrainExist(String name){
        return trainDAO.doesTrainExist(name);
    }

}
