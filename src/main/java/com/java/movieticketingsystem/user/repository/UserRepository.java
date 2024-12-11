package com.java.movieticketingsystem.user.repository;

import com.java.movieticketingsystem.user.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Fetches the user information by email id
     *
     * @param emailId The email id of the user
     * @return The optional valued user object.
     */
    Optional<User> findByEmail(@NonNull String emailId);
    /**
     * Checks if the email is already in the database or not.
     *
     * @param email The email id of the user.
     * @return The flag indicating whether the email exists in the system or not.
     */
    boolean existsByEmail(String email);
}
