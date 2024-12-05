package com.java.movieticketingsystem.movie.service;

import com.java.movieticketingsystem.Exception.ResourceNotFoundException;
import com.java.movieticketingsystem.movie.model.Movie;
import com.java.movieticketingsystem.movie.repository.MovieRepository;
import com.java.movieticketingsystem.utils.constants.MovieConstants;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService {
    @Autowired
    private MovieRepository movieRepository;


    @Override
    public Movie save(@NonNull Movie movie) {
        return movieRepository.save(movie);
    }


    @Override
    public List<Movie> findAll() {
        // Fetch all movies from the database
        return movieRepository.findAll();
    }

    @Override
    public Movie save(Long entity) {
        return null;
    }

    @Override
    public Movie findById(long id) {
        // Fetch movie by ID or throw IllegalArgumentException with a constant message
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MovieConstants.NOT_FOUND));
    }

    @Override
    public String update(long id, Long entity) {
        return "";
    }

    @Override
    public String update(Movie entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
        return null;
    }

    @Override
    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        // Update the fields as needed
        existingMovie.setMovieName(movieDetails.getMovieName());
        existingMovie.setGenre(movieDetails.getGenre());
        existingMovie.setDuration(String.valueOf(movieDetails.getDuration()));
        existingMovie.setDescription(movieDetails.getDescription());
        // Save the updated movie back to the database
        return movieRepository.save(existingMovie);
    }

}
