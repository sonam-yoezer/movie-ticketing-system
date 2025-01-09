package com.java.movieticketingsystem.ticket.service;

import com.java.movieticketingsystem.ticket.model.SeatStatusDTO;
import com.java.movieticketingsystem.ticket.model.Ticket;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.utils.IGenericCrudService;

import java.util.List;
import java.util.Optional;

public interface TicketService extends IGenericCrudService<Ticket, Ticket>{
    boolean isSeatBooked(Long theatreId, int seatNumber);
    void deleteTicket(Long ticketId);
    List<SeatStatusDTO> findByMovieAndTheatre(Long movieId, Long theatreId);
    List<Ticket> findTicketsByUser(User user);

    // New method to fetch a single ticket by its ID and the logged-in user's email
    Optional<Ticket> findByIdAndUser(Long ticketId, String email);
}
