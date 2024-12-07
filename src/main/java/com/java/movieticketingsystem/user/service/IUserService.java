package com.java.movieticketingsystem.user.service;


import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import com.java.movieticketingsystem.user.model.UserUpdateDTO;
import com.java.movieticketingsystem.utils.IGenericCrudService;

public interface IUserService extends IGenericCrudService <User, UserDTO> {

    /**
     * Fetches the authenticated instructor info.
     *
     * @return The instructor dto
     */
    UserDTO fetchSelfInfo();

    String updatePartial(long id, UserUpdateDTO updateDTO);
}

