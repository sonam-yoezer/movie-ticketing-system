package com.java.movieticketingsystem.Movie.model;

import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.ticket.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private long id;
    private String movieName;
    private String genre;
    private LocalDateTime releaseDate;
    private String duration;
    private Theatre theatre;
    private List<Ticket> tickets;
    private String image;
    private String description;

}
