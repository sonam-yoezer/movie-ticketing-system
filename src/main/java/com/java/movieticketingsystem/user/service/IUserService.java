package com.java.movieticketingsystem.user.service;

import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import com.java.movieticketingsystem.user.model.UserUpdateDTO;
import com.java.movieticketingsystem.utils.IGenericCrudService;
import lombok.NonNull;

public interface IUserService extends IGenericCrudService<User, UserDTO> {
    UserDTO fetchById(long id);

    User findById(long id);

    /**
     * Fetches the authenticated user info.
     *
     * @return The user dto
     */
    UserDTO fetchSelfInfo();

    String update(long id, @NonNull UserDTO userDTO);

    String update(long id, @NonNull UserUpdateDTO userUpdateDTO);

    /**
     * Updates the enabled/disabled status of a user.
     *
     * @param id The ID of the user
     * @param isEnabled The new status of the user (true = enabled, false = disabled)
     * @return Success message
     */
    String updateUserStatus(long id, boolean isEnabled);
}

