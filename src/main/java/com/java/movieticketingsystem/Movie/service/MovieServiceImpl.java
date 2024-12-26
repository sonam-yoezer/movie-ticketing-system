package com.java.movieticketingsystem.Movie.service;

import com.java.movieticketingsystem.Exception.ResourceNotFoundException;
import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.Movie.repository.MovieRepository;
import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.theatre.repository.TheatreRepository;
import com.java.movieticketingsystem.theatre.service.TheatreService;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private TheatreRepository theatreRepository;

    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("theaters", theatreService.findAll()); // Add theater list to the model
        model.addAttribute("movie", new Movie()); // Add empty movie object
        return "addMovie"; // Thymeleaf template name
    }

    @Override
    public Movie save(@NonNull Movie movie){
        try {
            if (movieRepository.existsByMovieName(movie.getMovieName())){
                throw new GlobalExceptionWrapper.BadRequestException("Movie with name '" + movie.getMovieName() + "' already exists ");
            }
            return movieRepository.save(movie);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalExceptionWrapper.BadRequestException("Movie with this name already exists");
        }
    }

    @Override
    public String update(long id, Long entity) {
        return "";
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
    public Movie fetchById(long id) throws Exception {
        return null;
    }

    @Override
    public String update(long id, Movie entity) {
        return "";
    }

    @Override
    public Movie findById(long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(
                        String.format("Movie not found with id: %d", id)));
    }

    @Override
    public String update(Movie entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        // Fetch the movie by ID. Throws an exception if not found.
        Movie movie = findById(id);

        // Delete the movie using the repository.
        movieRepository.delete(movie);

        // Return a success message.
        return "Movie with ID " + id + " has been deleted successfully.";
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

        // Handle Theatre update (assuming movieDetails.getTheatre() provides the id)
        if (movieDetails.getTheatre() != null) {
            Theatre theatre = theatreRepository.findById(movieDetails.getTheatre().getId()) // Use theatre ID
                    .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + movieDetails.getTheatre().getId()));
            existingMovie.setTheatre(theatre);
        }

        // Save the updated movie back to the database
        return movieRepository.save(existingMovie);
    }

}
