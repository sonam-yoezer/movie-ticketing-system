package com.java.movieticketingsystem.user.controller;

import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import com.java.movieticketingsystem.user.service.UserServiceImpl;
import com.java.movieticketingsystem.utils.RestHelper;
import com.java.movieticketingsystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetch self info of the User
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.fetchSelfInfo());
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
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("enabledUsers", userServiceImpl.findAll());
        listHashMap.put("disabledUsers", userServiceImpl.findAllDisabled());
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
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }



    /**
     * Deletes the user by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted user entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        try {
            String message = userServiceImpl.deleteById(id);
            return RestHelper.responseMessage(message);
        } catch (Exception e) {
            return RestHelper.responseError(e.getMessage());
        }
    }

    /**
     * Updates partial information of the user.
     *
     * @param id      The user id to update
     * @param userDTO The DTO containing fields to update
     * @return Response containing update confirmation message
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<RestResponse> updateUser(
            @PathVariable long id,
            @Validated @RequestBody UserDTO userDTO) {  // Changed from UserUpdateDTO to UserDTO
        try {
            String message = userServiceImpl.update(id, userDTO);
            return RestHelper.responseMessage(message);
        } catch (Exception e) {
            return RestHelper.responseError(e.getMessage());
        }
    }

       /**
    * Disables the user by id.
    *
    * @param id The unique identifier of the entity.
    * @return The message indicating the confirmation on disabled user entity.
    */
       @PutMapping("/{id}/disable")
       @PreAuthorize("hasAuthority('ADMIN')")
       public ResponseEntity<RestResponse> disableUser(@PathVariable long id) {
           String message = userServiceImpl.updateUserStatus(id, false); // Disable user
           return RestHelper.responseMessage(message);
       }

        @PutMapping("/{id}/enable")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<RestResponse> enableUser(@PathVariable long id) {
            String message = userServiceImpl.updateUserStatus(id, true); // Enable user
            return RestHelper.responseMessage(message);
        }

}
