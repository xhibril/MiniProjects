package org.example.trainsys.model;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private String email;
    private Long userId;
    private Long trainId;
    private String name;
    private LocalDateTime depTime;
    private String details;
    private String bookingDate;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDepTime(LocalDateTime depTime){
        this.depTime = depTime;
    }

    public LocalDateTime getDepTime(){
        return depTime;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String getDetails(){
        return details;
    }

    public void setBookingDate(String bookingDate){ this.bookingDate = bookingDate;}
    public String getBookingDate(){ return bookingDate;}


    public void setPrice(Double price){ this.price = price;}
    public Double getPrice(){ return price;}
}
