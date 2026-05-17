package org.example.trainsys.dao;

import org.example.trainsys.model.Seat;
import org.example.trainsys.model.Train;
import org.example.trainsys.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {


    public List<Train> getTrains() {
        String sql = "SELECT * FROM trains";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            return returnedTrains(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Train> getFilteredTrains(String dep, String dest){
        String sql = "SELECT * FROM trains WHERE departure = ? AND destination = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, dep);
            ps.setString(2, dest);

            ResultSet rs = ps.executeQuery();

         return returnedTrains(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Train> returnedTrains(ResultSet rs) throws SQLException {
        List<Train> trains = new ArrayList<>();
        while (rs.next()) {
            Train train = new Train();

            train.setName(rs.getString("train_name"));
            train.setDep(rs.getString("departure"));
            train.setDest(rs.getString("destination"));
            train.setTime(rs.getString("time"));
            train.setDate(rs.getString("date"));
            train.setSeats(rs.getInt("seats"));
            train.setId(rs.getLong("id"));

            trains.add(train);
        }
        return trains;
    }





    public boolean updateTrainDetails(Train train) {
        String sql = "UPDATE trains SET departure = ?, destination = ?, time = ?, date = ? WHERE id = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, train.getDep());
            ps.setString(2, train.getDest());
            ps.setString(3, train.getTime());
            ps.setString(4, train.getDate());
            ps.setLong(5, train.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }


    public boolean deleteTrain(Long trainId) {
        String seatsSql = "DELETE FROM seats WHERE train_id = ?";
        String ticketsSql = "DELETE FROM tickets WHERE train_id = ?";
        String trainsSql = "DELETE FROM trains WHERE id = ?";
        try {
            PreparedStatement seatsPs = DBConnection.getConn().prepareStatement(seatsSql);
            seatsPs.setLong(1, trainId);
            seatsPs.executeUpdate();

            PreparedStatement ticketsPs = DBConnection.getConn().prepareStatement(ticketsSql);
            ticketsPs.setLong(1, trainId);
            ticketsPs.executeUpdate();

            PreparedStatement trainPs = DBConnection.getConn().prepareStatement(trainsSql);
            trainPs.setLong(1, trainId);
            trainPs.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean doesTrainExist(String name){
        String sql = "SELECT * FROM trains WHERE train_name = ?";

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean createTrain(Train train) {
        String trainSql = "INSERT INTO trains (train_name, departure, destination, date, time, seats) VALUES (?,?,?,?,?,?)";
        String seatSql = "INSERT INTO seats (train_id, seat_number, is_booked) VALUES (?,?,false)";

        try {
            PreparedStatement ps = DBConnection.getConn()
                    .prepareStatement(trainSql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, train.getName());
            ps.setString(2, train.getDep());
            ps.setString(3, train.getDest());
            ps.setString(4, train.getDate());
            ps.setString(5, train.getTime());
            ps.setInt(6, train.getSeats());

            ps.executeUpdate();

            // get gen train id
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long trainId = rs.getLong(1);

                PreparedStatement seatPs = DBConnection.getConn().prepareStatement(seatSql);
                for (int i = 1; i <= train.getSeats(); i++) {
                    seatPs.setLong(1, trainId);
                    seatPs.setInt(2, i);
                    seatPs.addBatch(); // store
                }
                seatPs.executeBatch(); // insert
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Seat> getSeats(Long trainId) {
        String sql = "Select * FROM seats WHERE train_id = ?";
        List<Seat> seats = new ArrayList<>();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setLong(1, trainId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();

                seat.setUserId(rs.getLong("user_id"));
                seat.setSeatNumber(rs.getInt("seat_number"));
                seat.setBooked(rs.getBoolean("is_booked"));
                seat.setBookedBy(rs.getString("booked_by"));

                seats.add(seat);
            }
            return seats;
        } catch (SQLException e) {
            return null;
        }
    }


        public Seat getSeatDetails ( int seatNumber, Long trainId){
            String sql = "SELECT seat_number, is_booked, booked_by, email FROM seats WHERE train_id = ? AND seat_number = ?";

            try {
                PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
                ps.setLong(1, trainId);
                ps.setInt(2, seatNumber);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Seat seat = new Seat();

                    seat.setSeatNumber(rs.getInt("seat_number"));
                    seat.setBooked(rs.getBoolean("is_booked"));
                    seat.setBookedBy(rs.getString("booked_by"));
                    seat.setEmail(rs.getString("email"));

                    return seat;
                }

                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }
}
