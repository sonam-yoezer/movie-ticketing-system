package com.java.movieticketingsystem.theatre.service;

import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.theatre.repository.TheatreRepository;
import com.java.movieticketingsystem.utils.constants.ExceptionConstants;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper;
import com.java.movieticketingsystem.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService implements ITheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public List<Theatre> findAll() {
        // Fetch all theaters from the database
        return theatreRepository.findAll();
    }

    @Override
    public Theatre save(Theatre theatre) {
        try {
            return theatreRepository.save(theatre);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalExceptionWrapper.BadRequestException(ExceptionConstants.THEATRE_NAME_EXISTS);
        }
    }
    @Override
    public Theatre fetchById(long id) {
        return findById(id);
    }

    @Override
    public Theatre findById(long id) {
        return theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + id));
    }

    @Override
    public String update(long id, Theatre theatre) {
        Theatre existingTheatre = findById(id);
        updateTheatre(id, theatre);
        return "Theatre updated successfully";
    }

    @Override
    public String deleteById(long id) {
        Theatre theatre = findById(id);
        theatreRepository.delete(theatre);
        return "Theatre deleted successfully";
    }

    @Override
    public Theatre updateTheatre(Long id, Theatre theatreDetails) {
        Theatre existingTheatre = findById(id);
        
        existingTheatre.setName(theatreDetails.getName());
        existingTheatre.setLocation(theatreDetails.getLocation());
        existingTheatre.setSeatingCapacity(theatreDetails.getSeatingCapacity());
        existingTheatre.setFacilities(theatreDetails.getFacilities());
        existingTheatre.setContactDetails(theatreDetails.getContactDetails());
        
        return theatreRepository.save(existingTheatre);
    }

}
