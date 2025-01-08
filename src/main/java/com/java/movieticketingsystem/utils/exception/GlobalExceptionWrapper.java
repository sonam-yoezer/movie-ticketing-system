package com.java.movieticketingsystem.utils.exception;

import com.java.movieticketingsystem.utils.RestResponse;
import com.java.movieticketingsystem.utils.constants.ExceptionConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Contains all custom exceptions with corresponding constructor enabled.
 */
public class GlobalExceptionWrapper {

    private GlobalExceptionWrapper() {
    }

    /**
     * Returns the bad request exception with the given message and bad request http status code
     */
    @Getter
    public static class BadRequestException extends RuntimeException implements IGlobalException {

        private final HttpStatus httpStatus;

        public BadRequestException(String message) {
            super(message);
            this.httpStatus = HttpStatus.BAD_REQUEST;
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            return ResponseEntity.badRequest().body(setErrorResponse(exception));
        }
    }

    /**
     * Returns the not found exception with the given message and not found http status code
     */
    @Getter
    public static class NotFoundException extends RuntimeException implements IGlobalException {

        private final HttpStatus httpStatus;

        public NotFoundException(String message) {
            super(message);
            this.httpStatus = HttpStatus.NOT_FOUND;
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            RestResponse errorResponse = setErrorResponse(exception);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns the custom io exception with the given message and internal server error http status
     * code
     */
    @Getter
    public static class FileOperationException extends IOException implements IGlobalException {

        public FileOperationException(String message) {
            super(message);
        }
    }

    /**
     * Returns the generic exception with the exception message and internal server error http status
     * code
     */
    @Getter
    public static class GenericException extends Exception implements IGlobalException {

        private final Exception exception;

        public GenericException(Exception exception) {
            this.exception = exception;
        }
    }

    public static class UnauthorizedException extends RuntimeException implements IGlobalException {
        public UnauthorizedException(String message) {
            super(message);
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            RestResponse restResponse = setErrorResponse(exception);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restResponse);
        }
    }

    public static class UnauthorizedAccessException extends RuntimeException implements IGlobalException {
        public UnauthorizedAccessException(String message) {
            super(message);
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            RestResponse restResponse = setErrorResponse(exception);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restResponse);
        }
    }

    public static class TokenExpiredException extends RuntimeException implements IGlobalException {
        public TokenExpiredException(String message) {
            super(message);
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            RestResponse restResponse = setErrorResponse(exception);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restResponse);
        }
    }

    public static class UnauthorizedMovieOperationException extends RuntimeException implements IGlobalException {
        public UnauthorizedMovieOperationException() {
            super(ExceptionConstants.UNAUTHORIZED_MOVIE_OPERATION);
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            RestResponse restResponse = setErrorResponse(exception);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restResponse);
        }
    }

    @Getter
    public static class SeatValidationException extends RuntimeException implements IGlobalException {
        private final HttpStatus httpStatus;

        public SeatValidationException(String message) {
            super(message);
            this.httpStatus = HttpStatus.BAD_REQUEST;
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            return ResponseEntity.badRequest().body(setErrorResponse(exception));
        }
    }

    @Getter
    public static class MovieBookingException extends RuntimeException implements IGlobalException {
        private final HttpStatus httpStatus;

        public MovieBookingException(String message) {
            super(message);
            this.httpStatus = HttpStatus.BAD_REQUEST;
        }

        public MovieBookingException(String message, HttpStatus httpStatus) {
            super(message);
            this.httpStatus = httpStatus;
        }

        @Override
        public ResponseEntity<RestResponse> getResponse(Exception exception) {
            return ResponseEntity.status(httpStatus).body(setErrorResponse(exception));
        }
    }

    /**
     * Specific exception for booking-related issues
     */
    @Getter
    public static class BookingValidationException extends MovieBookingException {
        public BookingValidationException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Specific exception for show timing related issues
     */
    @Getter
    public static class ShowTimingException extends MovieBookingException {
        public ShowTimingException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }
}
