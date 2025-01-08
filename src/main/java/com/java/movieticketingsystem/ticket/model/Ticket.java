package com.java.movieticketingsystem.ticket.model;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.Random;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int ticketNumber;

    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Other fields, such as booking time, etc.

    @PrePersist
    private void generateTicketNumber() {
        this.ticketNumber = generateRandomTicketNumber();
    }

    // Method to generate a random 6-digit number
    private int generateRandomTicketNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Generates a 6-digit number
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
