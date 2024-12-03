package com.java.movieticketingsystem.utils.exception;

import com.java.movieticketingsystem.utils.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Function to get stack trace in string format.
     *
     * @param throwable - Throwable for all errors and exception.
     * @return String - Formatted stacktrace in string format.
     */
    public static String getStackTraceAsString(Throwable throwable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(outputStream)) {
            throwable.printStackTrace(printStream);
        }

        return outputStream.toString();
    }

    /**
     * Handler to handle exceptions thrown from the application.
     *
     * @param exception The exception thrown at runtime.
     * @return The proper error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleException(Exception exception, WebRequest request) {
        logger.error(exception.getMessage());
        IGlobalException globalException;
        if (exception instanceof IGlobalException) {
            globalException = (IGlobalException) exception;
        } else {
            globalException = new GlobalExceptionWrapper.GenericException(exception);
        }
        return globalException.getResponse(exception);
    }
}
