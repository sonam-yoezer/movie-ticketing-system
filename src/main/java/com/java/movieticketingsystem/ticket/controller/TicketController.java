package com.java.movieticketingsystem.ticket.controller;

import com.java.movieticketingsystem.ticket.model.SeatStatusDTO;
import com.java.movieticketingsystem.ticket.model.Ticket;
import com.java.movieticketingsystem.ticket.service.TicketService;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController{
    @Autowired
    private TicketService ticketService;

    // POST request to create a ticket
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @AuthenticationPrincipal User currentUser) {
        // Set the current logged-in user as the user for this ticket
        ticket.setUser(currentUser);

        // Call the service layer to save the ticket
        Ticket savedTicket = ticketService.save(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping("/check-seat")
    public ResponseEntity<String> checkSeatAvailability(@RequestParam Long theatreId, @RequestParam int seatNumber) {
        boolean isBooked = ticketService.isSeatBooked(theatreId, seatNumber); // calling the service method here
        if (isBooked) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat " + seatNumber + " is already booked.");
        } else {
            return ResponseEntity.ok("Seat " + seatNumber + " is available.");
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>("Ticket unbooked successfully", HttpStatus.OK);
    }

    /**
     * Endpoint to get the seat statuses for a specific movie and theatre.
     *
     * @param movieId   The ID of the movie.
     * @param theatreId The ID of the theatre.
     * @return A list of SeatStatusDTO objects.
     */
    @GetMapping("/seats/status")
    public ResponseEntity<List<SeatStatusDTO>> getSeatStatus(
            @RequestParam Long movieId,
            @RequestParam Long theatreId) {
        try {
            List<SeatStatusDTO> seatStatusList = ticketService.findByMovieAndTheatre(movieId, theatreId);
            return new ResponseEntity<>(seatStatusList, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            // Handle not found case
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            // Handle other errors
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
