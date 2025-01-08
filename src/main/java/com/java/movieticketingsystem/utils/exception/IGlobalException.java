package com.java.movieticketingsystem.utils.exception;

import com.java.movieticketingsystem.utils.RestResponse;
import org.springframework.http.ResponseEntity;

public interface IGlobalException {
    /**
     * Prepares the rest response from the exception encountered.
     *
     * @param exception The unhandled exception encountered.
     * @return The rest response wrapped with Http response.
     */
    default ResponseEntity<RestResponse> getResponse(Exception exception) {
        RestResponse restResponse = setErrorResponse(exception);
        return ResponseEntity.internalServerError().body(restResponse);
    }

    /**
     * Prepares the rest response from the custom exception encountered.
     *
     * @param exception The custom exception being handled.
     * @return The rest response wrapped with Http response.
     */
    default RestResponse setErrorResponse(Exception exception) {
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(false);
        restResponse.setMessage(exception.getMessage());
        return restResponse;
    }
}
