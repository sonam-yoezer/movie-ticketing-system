package com.java.movieticketingsystem.Movie.controller;

import com.java.movieticketingsystem.movie.model.Movie;
import com.java.movieticketingsystem.movie.service.MovieService;
import com.java.movieticketingsystem.utils.constants.MovieConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.findAll();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no movies found
        }
        return ResponseEntity.ok(movies);
    }

    // GET endpoint to fetch a movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable long id) {
        try {
            Movie movie = movieService.findById(id);
            return ResponseEntity.ok(movie.toString()); // Return 200 OK with the movie
        } catch (Exception e) {
            return ResponseEntity.status(404).body(MovieConstants.NOT_FOUND); // Return 404 with the constant message
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable long id) {
        try {
            movieService.deleteById(id);
            return ResponseEntity.ok("Movie deleted successfully."); // Return 200 OK with success message
        } catch (Exception e) {
            return ResponseEntity.status(404).body(MovieConstants.NOT_FOUND); // Return 404 if the movie is not found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @Validated @RequestBody Movie movieDetails) {
        Movie updatedMovie = movieService.updateMovie(id, movieDetails);
        return ResponseEntity.ok(updatedMovie);
    }
}






