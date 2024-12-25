package com.java.movieticketingsystem.Movie.controller;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.service.MovieService;
import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.theatre.repository.TheatreRepository;
import com.java.movieticketingsystem.theatre.service.TheatreService;
import com.java.movieticketingsystem.utils.RestHelper;
import com.java.movieticketingsystem.utils.RestResponse;
import com.java.movieticketingsystem.utils.constants.MovieConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private TheatreRepository theatreRepository;


    @GetMapping("/theaters")
    public ResponseEntity<List<Theatre>> findAll() {
        return ResponseEntity.ok(theatreService.findAll()); // Return theater list in JSON
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can create movies
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getTheatre() == null) {
            throw new RuntimeException("Theatre information is missing.");
        }

        // Fetch the Theatre object by ID
        Theatre theatre = theatreRepository.findById(movie.getTheatre().getId())
                .orElseThrow(() -> new RuntimeException("Theatre with ID " + movie.getTheatre().getId() + " does not exist."));

        // Set the Theatre in the Movie
        movie.setTheatre(theatre);

        // Save the Movie with the associated Theatre
        Movie savedMovie = movieService.save(movie);
        return ResponseEntity.ok(savedMovie);
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
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Allow both USER and ADMIN roles
    public ResponseEntity<RestResponse> getMovieId(@PathVariable long id){
        try {
            Movie movie = movieService.findById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("movie", movie);
            return RestHelper.responseSuccess(response);
        } catch (Exception e){
            return RestHelper.responseError(MovieConstants.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can create movies
    public ResponseEntity<String> deleteMovieById(@PathVariable long id) {
        try {
            movieService.deleteById(id);
            return ResponseEntity.ok("Movie deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Movie not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @Validated @RequestBody Movie movieDetails) {
        Movie updatedMovie = movieService.updateMovie(id, movieDetails);
        return ResponseEntity.ok(updatedMovie);
    }

}
