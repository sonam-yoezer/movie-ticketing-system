package com.java.movieticketingsystem.utils.exception;

import com.java.movieticketingsystem.utils.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Logs the full stack trace for debugging purposes.
     *
     * @param throwable - Throwable for all errors and exception.
     */
    private void logStackTrace(Throwable throwable) {
        logger.error("Exception occurred:", throwable);
    }

    /**
     * Handler to handle exceptions thrown from the application.
     *
     * @param exception The exception thrown at runtime.
     * @param request The web request
     * @return The proper error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleException(Exception exception, WebRequest request) {
        // Log the full exception for debugging
        logStackTrace(exception);

        IGlobalException globalException;
        if (exception instanceof IGlobalException) {
            globalException = (IGlobalException) exception;
        } else {
            globalException = new GlobalExceptionWrapper.GenericException(exception);
        }
        return globalException.getResponse(exception);
    }
}
