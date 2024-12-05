package com.java.movieticketingsystem.movie.repository;
import com.java.movieticketingsystem.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    /*
      JpaRepository contains the code needed to interact with the database by providing basic CRUD operations
      (like save(), findById(), findAll(), and deleteById()) out of the box. It also offers support for custom
      queries, pagination, and sorting, which makes database operations easier without needing to write SQL manually.
       By extending JpaRepository, your repository inherits all these methods, saving you time and reducing the
      amount of code you have to write for database interactions.
     */
}
