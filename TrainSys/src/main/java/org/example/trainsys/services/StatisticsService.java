package org.example.trainsys.services;
import org.example.trainsys.dao.TicketDAO;
import org.example.trainsys.model.Ticket;
import org.example.trainsys.model.Train;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsService {

    private final TicketService ticketService = new TicketService();
    private final TrainService trainService = new TrainService();

    private final TicketDAO ticketDAO = new TicketDAO();

    public Map<String, Integer> getTicketsSoldPerTrain() {
        List<Train> trains = trainService.getTrains();
        List<Ticket> tickets = ticketService.getAllTickets();

        // train id, tickets sold
        Map<Long, Integer> ticketsMap = new HashMap<>();

        // train name, train id
        Map<String, Long> trainsMap = new HashMap<>();

        // train name, tickets sold
        Map<String, Integer> finalizedMap = new HashMap<>();

        for (Ticket ticket : tickets) {
            Long trainId = ticket.getTrainId();
            ticketsMap.put(trainId, ticketsMap.getOrDefault(trainId, 0) + 1
            );
        }

        for(Train train : trains){
            String trainName = train.getName();
            trainsMap.put(trainName, train.getId());
        }

        trainsMap.forEach((key, value) -> {
            if(ticketsMap.containsKey(value)){
                finalizedMap.put(key, ticketsMap.get(value));
            }
        });

        return finalizedMap;
    }



    public Double getRevenue(LocalDate from, LocalDate to){
        List<Ticket> tickets = ticketDAO.getTicketsBetweenDates(from.toString(), to.toString());
        Double revenue = 0.0;

        for(Ticket ticket : tickets){
            revenue += ticket.getPrice();
        }

        return revenue;
    }
}
