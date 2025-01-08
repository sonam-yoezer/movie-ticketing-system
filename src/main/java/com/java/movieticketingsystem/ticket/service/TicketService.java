package com.java.movieticketingsystem.ticket.service;

import com.java.movieticketingsystem.ticket.model.SeatStatusDTO;
import com.java.movieticketingsystem.ticket.model.Ticket;
import com.java.movieticketingsystem.utils.IGenericCrudService;

import java.util.List;

public interface TicketService extends IGenericCrudService<Ticket, Ticket>{
    boolean isSeatBooked(Long theatreId, int seatNumber);
    void deleteTicket(Long ticketId);
    List<SeatStatusDTO> findByMovieAndTheatre(Long movieId, Long theatreId);
}
