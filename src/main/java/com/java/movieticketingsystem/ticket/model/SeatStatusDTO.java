package com.java.movieticketingsystem.ticket.model;

public class SeatStatusDTO {
    private int seatNumber;
    private boolean isBooked;

    public SeatStatusDTO(int seatNumber, boolean isBooked) {
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    // Getters and setters
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}
