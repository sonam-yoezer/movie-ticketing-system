package com.java.movieticketingsystem.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String name;
    private String phoneNumber;
    private String email;
    
    // Custom validation to ensure at least one field is present
    public boolean hasAtLeastOneField() {
        return name != null || phoneNumber != null || email != null;
    }
}