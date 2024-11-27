package com.java.movieticketingsystem.Movie.controller;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie saveMovie(@Validated @RequestBody Movie movie) {
        return movieService.save(movie);
    }
}





