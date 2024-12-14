package com.java.movieticketingsystem.theatre.controller;

import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.theatre.service.TheatreService;
import com.java.movieticketingsystem.utils.constants.ExceptionConstants;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper.UnauthorizedAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theatres")
@PreAuthorize("hasAuthority('ADMIN')")  // Ensures only admin can access these endpoints
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping
    public ResponseEntity<Theatre> createTheatre(@Validated @RequestBody Theatre theatre) {
        try {
            Theatre savedTheatre = theatreService.save(theatre);
            return ResponseEntity.ok(savedTheatre);
        } catch (AccessDeniedException e) {
            throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_THEATRE_OPERATION);
        }
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        List<Theatre> theatres = theatreService.findAll();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable long id) {
        Theatre theatre = theatreService.findById(id);
        return ResponseEntity.ok(theatre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Theatre> updateTheatre(@PathVariable Long id, @Validated @RequestBody Theatre theatreDetails) {
        Theatre updatedTheatre = theatreService.updateTheatre(id, theatreDetails);
        return ResponseEntity.ok(updatedTheatre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTheatre(@PathVariable long id) {
        String message = theatreService.deleteById(id);
        return ResponseEntity.ok(message);
    }

}
