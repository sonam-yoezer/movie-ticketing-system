package com.java.movieticketingsystem.email.service;

public interface EmailService {
    /**
     * Sends the password reset link to the dedicated email from the sender email.
     *
     * @param toEmail    The email to whom the password is to be sent.
     * @param resetToken The token which is to be used to reset the token.
     */
    void sendResetPasswordEmail(String toEmail, String resetToken);
}
