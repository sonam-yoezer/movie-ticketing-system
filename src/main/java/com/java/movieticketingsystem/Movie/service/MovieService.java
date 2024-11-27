package com.java.movieticketingsystem.Movie.service;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.repository.MovieRepository;
import com.java.movieticketingsystem.constants.MovieConstants;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService{
    @Autowired
    private MovieRepository movieRepository;


    @Override
    public Movie save(@NonNull Movie movie) {
        //TODO: Validate for save
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> findAll() {
        // Fetch all movies from the database
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(long id) {
        // Fetch movie by ID or throw IllegalArgumentException with a constant message
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MovieConstants.NOT_FOUND));
    }

    @Override
    public String update(Movie entity) {
        return "";
    }

    @Override
    public void deleteById(long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }

}
