package com.java.movieticketingsystem.ticket.service;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.repository.MovieRepository;
import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.theatre.repository.TheatreRepository;
import com.java.movieticketingsystem.ticket.model.SeatStatusDTO;
import com.java.movieticketingsystem.ticket.model.Ticket;
import com.java.movieticketingsystem.ticket.repository.TicketRepository;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.repository.UserRepository;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper;
import com.java.movieticketingsystem.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public List<Ticket> findAll() {
        // Placeholder for future implementation to fetch all tickets
        return List.of();
    }

    @Override
    public Ticket fetchById(long id) throws Exception {
        // Placeholder for future implementation to fetch ticket by ID
        return null;
    }

    @Override
    public String update(long id, Ticket entity) {
        // Placeholder for future update logic
        return "";
    }

    @Override
    public String deleteById(long id) {
        // Placeholder for future delete logic
        return "";
    }

    /**
     * Saves a ticket after validating the movie, seat, and user.
     * It also assigns the logged-in user to the ticket.
     */

    // those method are for users
    @Override
    public Ticket save(Ticket ticket) {
        // Fetch and validate the Movie
        Movie movie = movieRepository.findById(ticket.getMovie().getId())
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException("Movie not found"));
        ticket.setMovie(movie);

        // Ensure the Movie has a Theatre
        Theatre theatre = movie.getTheatre();
        if (theatre == null) {
            throw new GlobalExceptionWrapper.MovieBookingException("Theatre information is missing in the movie.");
        }

        // Validate seat selection
        validateSeatSelection(theatre, ticket.getSeatNumber());

        // Fetch the logged-in user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();  // Assuming email is the username
        User user = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException("User not found"));
        ticket.setUser(user);  // Set the logged-in user to the ticket

        // Save and return the Ticket
        return ticketRepository.save(ticket);
    }

    /**
     * Validates if the selected seat is available for booking.
     * Throws an exception if the seat number is invalid or already reserved.
     */
    private void validateSeatSelection(Theatre theatre, Integer seatNumber) {
        if (seatNumber == null || seatNumber < 1 || seatNumber > theatre.getSeatingCapacity()) {
            throw new GlobalExceptionWrapper.SeatValidationException("Invalid seat number. Please select a valid seat.");
        }

        // Fetch reserved seats and check if the seat is already booked
        List<Integer> reservedSeats = ticketRepository.findReservedSeatsByTheatreId(theatre.getId());
        if (reservedSeats.contains(seatNumber)) {
            throw new GlobalExceptionWrapper.SeatValidationException("Selected seat is already reserved. Please choose another seat.");
        }
    }

    /**
     * Checks if the seat is already booked for a given theatre.
     *
     * @param theatreId  The ID of the theatre.
     * @param seatNumber The seat number to check.
     * @return True if the seat is booked, false otherwise.
     */
    public boolean isSeatBooked(Long theatreId, int seatNumber) {
        List<Integer> reservedSeats = ticketRepository.findReservedSeatsByTheatreId(theatreId);
        return reservedSeats.contains(seatNumber); // Returns true if the seat is reserved
    }

    /**
     * Deletes a ticket by its ID, effectively unbooking the seat.
     *
     * @param ticketId The ID of the ticket to delete.
     */
    @Override
    public void deleteTicket(Long ticketId) {
        // Find and delete the ticket (unbook the seat)
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    @Override
    public List<SeatStatusDTO> findByMovieAndTheatre(Long movieId, Long theatreId) {
        // Fetch the theatre and movie to ensure they exist
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // Fetch all reserved seat numbers for the given movie and theatre
        List<Integer> reservedSeats = ticketRepository.findReservedSeatsForMovieAndTheatre(movieId, theatreId);

        // Create a list of seat statuses for all seats in the theatre
        List<SeatStatusDTO> seatStatusList = new ArrayList<>();
        for (int seatNumber = 1; seatNumber <= theatre.getSeatingCapacity(); seatNumber++) {
            boolean isBooked = reservedSeats.contains(seatNumber); // Check if seat is booked
            seatStatusList.add(new SeatStatusDTO(seatNumber, isBooked));
        }

        return seatStatusList;
    }
    //underling method are for admin
}
