package org.example.trainsys.model;

public class Seat {

    private Long id;
    private Long trainId;
    private int seatNumber;
    private boolean isBooked;
    private String bookedBy;
    private Long ticketId;
    private Long userId;
    private String email;


    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }


    public void setBookedBy(String bookedBy){
        this.bookedBy = bookedBy;
    }

    public String getBookedBy(){
        return bookedBy;
    }


    public void setTicketId(Long ticketId){ this.ticketId = ticketId; }

    public Long getTicketId(){ return ticketId; }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setEmail(String email){ this.email = email;}

    public String getEmail(){ return email;}
}
