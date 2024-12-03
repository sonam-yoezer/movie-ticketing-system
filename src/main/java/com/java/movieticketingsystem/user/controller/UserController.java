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
     * Fetch self info of the instructor
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", userService.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the instructor by identifier.
     *
     * @param id The unique identifier of the instructor.
     * @return The instructor entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", userService.findById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the instructor entities in the system.
     *
     * @return The list of instructor entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructors", userService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Signing up the new instructor.
     *
     * @param instructor The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User instructor) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(instructor));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing instructor entity.
     *
     * @param instructor The updated instructor entity.
     * @return The message indicating the confirmation on updated instructor entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN','INSTRUCTOR')")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @Validated User instructor) {
        String message = userService.update(id, instructor);
        return RestHelper.responseMessage(message);
    }

    /**
     * Deletes the instructor by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted instructor entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userService.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
