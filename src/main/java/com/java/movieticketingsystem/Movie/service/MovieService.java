package com.java.movieticketingsystem.Movie.service;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.model.MovieDTO;
import com.java.movieticketingsystem.utils.IGenericCrudService;
import io.micrometer.common.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService extends IGenericCrudService<Long, Movie> {
    Movie save(@NonNull Movie movie);

    String update(long id, Long entity);

    Movie findById(long id);

    String update(Movie entity);

    Movie updateMovie(Long id, Movie movieDetails);

    /**
     * Updates the movie image for the authenticated user.
     *
     * @param file The file to be used as image.
     * @return The updated user information.
     */
    MovieDTO updateMovieImage(Long movieId, MultipartFile file);
}
