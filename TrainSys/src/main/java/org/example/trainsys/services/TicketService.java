package org.example.trainsys.services;
import org.example.trainsys.dao.TicketDAO;
import org.example.trainsys.dto.ApiResponse;
import org.example.trainsys.model.Seat;
import org.example.trainsys.model.Ticket;
import java.util.List;

public class TicketService {

    private final TicketDAO ticketDAO = new TicketDAO();

    public ApiResponse bookTicket(Ticket ticket, List<Seat> ticketSeats) {
        return ticketDAO.saveTicket(ticket, ticketSeats);
    }


    public List<Ticket> getTickets(Long userId) {
        return ticketDAO.getTickets(userId);
    }

    public List<Ticket> getAllTickets(){
        return ticketDAO.getAllTickets();
    }

    public Double calculateTicketPrice(Double price, String seatClass, String action) {
        switch (action) {
            case "ADD" -> price += "VIP".equals(seatClass) ? 500 : 200;
            case "SUBTRACT" -> price -= "VIP".equals(seatClass) ? 500 : 200;
        }

        return price;
    }


    public boolean cancelTicket(Long ticketId) {
        return ticketDAO.cancelTicket(ticketId);
    }


    public Ticket getTicketDepartureTime(Long ticketId){
        return ticketDAO.getTicketDepartureTime(ticketId);
    }


    public String getTicketSeats(List<Seat> seatsList){
        String seatsStr = "";

        for(Seat seat : seatsList){
            seatsStr +=  + seat.getSeatNumber() + " ";
        }
        return seatsStr;
    }
}
