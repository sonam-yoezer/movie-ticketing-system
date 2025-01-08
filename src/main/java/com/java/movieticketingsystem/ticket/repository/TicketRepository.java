package com.java.movieticketingsystem.ticket.repository;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.ticket.model.SeatStatusDTO;
import com.java.movieticketingsystem.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t.seatNumber FROM Ticket t WHERE t.movie.theatre.id = :theatreId")
    List<Integer> findReservedSeatsByTheatreId(@Param("theatreId") Long theatreId);

    /**
     * Retrieves the seat status (booked or not) for a given movie and theatre.
     * @param movieId The ID of the movie.
     * @param theatreId The ID of the theatre.
     * @return A list of SeatStatusDTO indicating booked or available status for each seat.
     */
    @Query("SELECT new com.java.movieticketingsystem.ticket.model.SeatStatusDTO(t.seatNumber, true) " +
            "FROM Ticket t WHERE t.movie.id = :movieId AND t.movie.theatre.id = :theatreId")
    List<SeatStatusDTO> findBookedSeatsForMovieAndTheatre(@Param("movieId") Long movieId, @Param("theatreId") Long theatreId);

    /**
     * Retrieves all available seats (unbooked) for a given theatre and movie.
     * @param movieId The ID of the movie.
     * @param theatreId The ID of the theatre.
     * @return A list of SeatStatusDTO for unbooked seats.
     */
    @Query("SELECT t.seatNumber FROM Ticket t WHERE t.movie.id = :movieId AND t.movie.theatre.id = :theatreId")
    List<Integer> findReservedSeatsForMovieAndTheatre(@Param("movieId") Long movieId, @Param("theatreId") Long theatreId);

}
