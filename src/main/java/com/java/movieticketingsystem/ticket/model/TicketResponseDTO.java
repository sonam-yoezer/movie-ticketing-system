package com.java.movieticketingsystem.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO<T> {
    private boolean status;
    private String message;
    private T data;

    public TicketResponseDTO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
