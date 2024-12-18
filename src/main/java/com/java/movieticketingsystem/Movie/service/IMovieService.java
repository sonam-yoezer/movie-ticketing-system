package com.java.movieticketingsystem.Movie.service;

import com.java.movieticketingsystem.Movie.model.Movie;
import com.java.movieticketingsystem.utils.IGenericCrudService;
import io.micrometer.common.lang.NonNull;

public interface IMovieService extends IGenericCrudService<Long, Movie> {
    Movie save(@NonNull Movie movie);

    String update(long id, Long entity);

    Movie findById(long id);

    String update(Movie entity);

    Movie updateMovie(Long id, Movie movieDetails);
    /*
The IMovieService interface extends IGenericCrudService<Movie>, which means it inherits all the common
CRUD operations for the Movie entity, such as save(), findById(), findAll(), update(), and delete().

By doing this, you can keep the MovieService class focused on business logic specific to movies while reusing
generic CRUD functionality provided by IGenericCrudService. This approach promotes code reuse, modularity,
and flexibility. If you need CRUD operations for other entities in the future, you can easily create similar
service interfaces for them.
    */
}