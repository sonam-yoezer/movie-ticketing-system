package com.java.movieticketingsystem.Movie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String movieName;

    private String genre;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private LocalTime duration;

    private String description;
}


