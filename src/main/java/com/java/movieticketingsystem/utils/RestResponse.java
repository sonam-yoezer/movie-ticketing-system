package com.java.movieticketingsystem.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse {

    private Boolean status;
    private String message;
    private String errorTrace;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> data = new HashMap<>();

    public void addPayload(String key, Object value) {
        if (value != null) {
            this.data.put(key, value);
        }
    }
}