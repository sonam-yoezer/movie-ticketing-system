package com.java.movieticketingsystem.email.repository;

import com.java.movieticketingsystem.email.model.PasswordResetToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    /**
     * Retrieves the password reset token entity for the dedicated token.
     *
     * @param token The token assigned for the dedicated user.
     * @return The optional valued password reset token entity.
     */
    Optional<PasswordResetToken> findByToken(String token);
    /**
     * Deletes any token present for the dedicated user.
     *
     * @param userId The user identifier of the user.
     */
    @Modifying
    @Transactional
    @Query(value = "delete from password_reset_token where user_id = :userId", nativeQuery = true)
    void deleteByUserId(long userId);
}
