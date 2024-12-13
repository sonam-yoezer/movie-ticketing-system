package com.java.movieticketingsystem.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Inherited;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "theatre")
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int seatingCapacity;

    private String facilities;

    private String contactDetails;
}
