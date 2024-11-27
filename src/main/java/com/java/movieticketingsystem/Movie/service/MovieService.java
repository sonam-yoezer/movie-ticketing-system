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
    public List<Movie> findAll() {
        return List.of();
    }

    @Override
    public Movie save(@NonNull Movie movie) {
        //TODO: Validate for save
        return movieRepository.save(movie);
    }

    @Override
    public Movie findById(long id) throws Exception {
        return null;
    }

    @Override
    public String update(Movie entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }

}
