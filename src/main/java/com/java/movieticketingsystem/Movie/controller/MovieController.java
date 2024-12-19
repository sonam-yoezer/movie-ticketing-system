package com.java.movieticketingsystem.Movie.controller;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.service.MovieService;
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

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can create movies
    public ResponseEntity<RestResponse> saveMovie(@Validated @RequestBody Movie movie){
        try{
            Movie savedMovie = movieService.save(movie);
            Map<String, Object> response = new HashMap<>();
            response.put("movie", savedMovie);
            return RestHelper.responseSuccess(response);
        } catch (Exception e){
            return RestHelper.responseError(e.getMessage());
        }
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
