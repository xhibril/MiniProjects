package org.example.trainsys.dao;
import org.example.trainsys.dto.ApiResponse;
import org.example.trainsys.model.Seat;
import org.example.trainsys.model.Ticket;
import org.example.trainsys.utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {


    public ApiResponse saveTicket(Ticket ticket, List<Seat> ticketSeats) {

        String ticketSql = """
            INSERT INTO tickets (user_id, train_id, dep_time, email, name, details, booking_date, price)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try {
            PreparedStatement ticketPs = DBConnection.getConn()
                    .prepareStatement(ticketSql, PreparedStatement.RETURN_GENERATED_KEYS);

            ticketPs.setLong(1, ticket.getUserId());
            ticketPs.setLong(2, ticket.getTrainId());
            ticketPs.setTimestamp(3, Timestamp.valueOf(ticket.getDepTime()));
            ticketPs.setString(4, ticket.getEmail());
            ticketPs.setString(5, ticket.getName());
            ticketPs.setString(6, ticket.getDetails());
            ticketPs.setString(7, ticket.getBookingDate());
            ticketPs.setDouble(8, ticket.getPrice());

            ticketPs.executeUpdate();

            String seatSql = """
                UPDATE seats 
                SET is_booked = 1, booked_by = ?, ticket_id = ?, user_id = ?, email = ?  
                WHERE train_id = ? AND seat_number = ?
                """;

            PreparedStatement ticketSeatPs = DBConnection.getConn().prepareStatement(seatSql);

            ResultSet rs = ticketPs.getGeneratedKeys();

            if (rs.next()) {
                long ticketId = rs.getLong(1);

                for (Seat currentSeat : ticketSeats) {


                    ticketSeatPs.setString(1, ticket.getName());
                    ticketSeatPs.setLong(2, ticketId);
                    ticketSeatPs.setLong(3, ticket.getUserId());
                    ticketSeatPs.setString(4, ticket.getEmail());
                    ticketSeatPs.setLong(5, ticket.getTrainId());
                    ticketSeatPs.setInt(6, currentSeat.getSeatNumber());

                    ticketSeatPs.executeUpdate();
                }
            }

            return new ApiResponse("Ticket successfully bought", true);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ApiResponse("Could not create ticket, try again", false);
        }
    }


    public List<Ticket> getTickets(Long userId){
            String sql = "SELECT * FROM tickets WHERE user_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            return returnTickets(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Ticket> getAllTickets(){
        String sql = "SELECT * FROM tickets";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            return returnTickets(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Ticket> returnTickets(ResultSet rs) throws SQLException {
        List<Ticket> ticketsList = new ArrayList<>();

        while(rs.next()){
            Ticket ticket = new Ticket();

            ticket.setId(rs.getLong("id"));
            ticket.setTrainId(rs.getLong("train_id"));
            ticket.setEmail(rs.getString("email"));
            ticket.setName(rs.getString("name"));
            ticket.setDetails(rs.getString("details"));
            ticket.setBookingDate(rs.getString("booking_date"));
            ticket.setPrice(rs.getDouble("price"));

            ticketsList.add(ticket);
        }

        return ticketsList;
    }


    public boolean cancelTicket(Long ticketId){
        String ticketsSql = "DELETE from tickets WHERE id = ?";
        String seatsSql = "UPDATE seats SET is_booked = false, booked_by = null, user_id = null, email = null WHERE ticket_id = ?";

        try {
            PreparedStatement ticketsPs = DBConnection.getConn().prepareStatement(ticketsSql);
            ticketsPs.setLong(1, ticketId);
            ticketsPs.executeUpdate();

            PreparedStatement seatsPs = DBConnection.getConn().prepareStatement(seatsSql);
            seatsPs.setLong(1, ticketId);
            seatsPs.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Ticket> getTicketsBetweenDates(String from, String to){
        String sql = "SELECT * FROM tickets WHERE booking_date BETWEEN ? AND ?";
        List<Ticket> ticketList = new ArrayList<>();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, from);
            ps.setString(2, to);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setPrice(Double.parseDouble(rs.getString("price")));
                ticketList.add(ticket);
            }

            return ticketList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Ticket getTicketDepartureTime(Long ticketId){

        String sql = "SELECT dep_time FROM tickets WHERE id = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);

            ps.setLong(1, ticketId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setDepTime(rs.getTimestamp("dep_time").toLocalDateTime());
                return ticket;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
