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
    User fetchSelfInfo();

    String update(long id, @NonNull UserDTO userDTO);

    String update(long id, @NonNull UserUpdateDTO userUpdateDTO);
}
