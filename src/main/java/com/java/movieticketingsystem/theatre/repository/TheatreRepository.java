package com.java.movieticketingsystem.theatre.repository;

import com.java.movieticketingsystem.theatre.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}
