package com.java.movieticketingsystem.Movie.mapper;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.model.MovieDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    // Converts Movie entity to MovieDTO
    public static MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            return null;
        }

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setMovieName(movie.getMovieName());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setTheatre(movie.getTheatre());
        movieDTO.setTickets(movie.getTickets());
        movieDTO.setImage(movie.getImage());
        movieDTO.setDescription(movie.getDescription());

        return movieDTO;
    }


    // Converts MovieDTO to Movie entity
    public static Movie toEntity(MovieDTO movieDTO) {
        if (movieDTO == null) {
            return null;
        }

        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setMovieName(movieDTO.getMovieName());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setTheatre(movieDTO.getTheatre());
        movie.setTickets(movieDTO.getTickets());
        movie.setImage(movieDTO.getImage());
        movie.setDescription(movieDTO.getDescription());
        return movie;
    }

    // Converts a list of Movie entities to a list of MovieDTOs
    public static List<MovieDTO> toDTOList(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            return null;
        }

        return movies.stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Converts a list of MovieDTOs to a list of Movie entities
    public static List<Movie> toEntityList(List<MovieDTO> movieDTOs) {
        if (movieDTOs == null || movieDTOs.isEmpty()) {
            return null;
        }

        return movieDTOs.stream()
                .map(MovieMapper::toEntity)
                .collect(Collectors.toList());
    }
}
