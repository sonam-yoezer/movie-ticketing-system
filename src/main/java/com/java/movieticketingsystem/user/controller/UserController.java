package com.java.movieticketingsystem.user.controller;

import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.service.UserService;
import com.java.movieticketingsystem.utils.RestHelper;
import com.java.movieticketingsystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }
}
