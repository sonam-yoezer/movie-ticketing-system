package com.java.movieticketingsystem.utils;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class RestHelper {

    private RestHelper() {
    }

    public static ResponseEntity<RestResponse> responseSuccess(Map<String, Object> data) {
        RestResponse restResponse = new RestResponse();
        if (!data.isEmpty()) {
            restResponse.setData(data);
            restResponse.setStatus(true);
        }
        return ResponseEntity.ok(restResponse);
    }

    public static ResponseEntity<RestResponse> responseError(String msg) {
        RestResponse restResponse = new RestResponse();
        restResponse.setError(msg);
        restResponse.setStatus(false);
        return ResponseEntity.ok(restResponse);
    }

    public static ResponseEntity<RestResponse> responseMessage(String msg) {
        RestResponse restResponse = new RestResponse();
        restResponse.setMessage(msg);
        restResponse.setStatus(true);
        return ResponseEntity.ok(restResponse);
    }
}
