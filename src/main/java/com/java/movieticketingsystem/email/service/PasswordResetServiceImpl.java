package com.java.movieticketingsystem.email.service;

import com.java.movieticketingsystem.auth.helper.JwtService;
import com.java.movieticketingsystem.email.dto.ForgotPasswordRequest;
import com.java.movieticketingsystem.email.dto.ResetPasswordRequest;
import com.java.movieticketingsystem.email.model.PasswordResetToken;
import com.java.movieticketingsystem.email.repository.PasswordResetTokenRepository;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.service.UserServiceImpl;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.java.movieticketingsystem.utils.constants.EmailConstants.*;
import static com.java.movieticketingsystem.utils.constants.UserConstants.NOT_FOUND_MESSAGE;
import static com.java.movieticketingsystem.utils.constants.UserConstants.USER;

@Service
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public String forgotPassword(@NonNull ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail()).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
        createPasswordResetTokenForUser(user);
        return SENT_RESET_PASSWORD_LINK;
    }

    /**
     * Creates the password reset token and generates the reset link for the email provided.
     *
     * @param user The user for whom the password is to be reset.
     */
    @Transactional
    private void createPasswordResetTokenForUser(User user) {
        String token = jwtService.generateToken(user.getEmail());
        PasswordResetToken myToken = new PasswordResetToken(user, token);
        tokenRepository.deleteByUserId(user.getId());
        tokenRepository.save(myToken);
        emailService.sendResetPasswordEmail(user.getEmail(), token);
    }
    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        validatePasswordResetToken(resetPasswordRequest.getToken());
        String email = jwtService.extractUsername(resetPasswordRequest.getToken());
        User user = userService.findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
        changePassword(user, resetPasswordRequest.getPassword());
        return PASSWORD_RESET_SUCCESSFUL;
    }
    /**
     * Validates the token provided for the password reset process.
     *
     * @param token The jwt token used to validate the authenticity of the targeted user email.
     */
    private void validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(INVALID_TOKEN));
        if (passToken.isExpired()) {
            tokenRepository.delete(passToken);
            throw new GlobalExceptionWrapper.BadRequestException(TOKEN_EXPIRED);
        }
    }
    /**
     * Changes the password for the given user and the password provided.
     *
     * @param user        The user entity of the user.
     * @param newPassword The new password for the user.
     */
    private void changePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        userService.updateEntity(user);
    }

}
