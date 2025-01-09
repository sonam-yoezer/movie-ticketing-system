package com.java.movieticketingsystem.Movie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.ticket.model.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "movie")
public class  Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String movieName;

    private String genre;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private String duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Ticket> tickets;

    private String image;

    private String description;

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //getter and setter method for update
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
