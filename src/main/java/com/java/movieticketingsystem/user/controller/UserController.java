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
     * Fetch self info of the User
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the user by identifier.
     *
     * @param id The unique identifier of the instructor.
     * @return The instructor entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.findById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the user entities in the system.
     *
     * @return The list of user entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

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

    /**
     * Updates the existing user entity.
     *
     * @param user The updated user entity.
     * @return The message indicating the confirmation on updated user entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN','USER')")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @Validated User user) {
        String message = userService.update(id, user);
        return RestHelper.responseMessage(message);
    }

    /**
     * Deletes the user by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted user entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userService.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
