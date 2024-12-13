package com.java.movieticketingsystem.theatre.service;

import com.java.movieticketingsystem.theatre.model.Theatre;
import com.java.movieticketingsystem.utils.IGenericCrudService;

public interface ITheatreService extends IGenericCrudService<Theatre, Theatre> {
    Theatre findById(long id);
    Theatre updateTheatre(Long id, Theatre theatreDetails);
}
