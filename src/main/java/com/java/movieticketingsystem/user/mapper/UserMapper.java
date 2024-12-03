package com.java.movieticketingsystem.user.mapper;


import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    /**
     * Maps the user to user dto.
     *
     * @param user The user entity.
     * @return Returns the instructor entity.
     */
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto, "password");
        return dto;
    }

    /**
     * Maps the list of user to user dto
     *
     * @param user The list of user entity
     * @return The list of user dto.
     */
    public static List<UserDTO> toDTO(List<User> user) {
        return user.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional user to optional user dto.
     *
     * @param user The instructor entity
     * @return The optional instructor dto.
     */
    public static Optional<UserDTO> toDTO(Optional<User> user) {
        return user.map(UserMapper::toDTO);
    }

    /**
     * Maps the user dto  to the user entity.
     *
     * @param dto The instructor dto.
     * @return The user entity.
     */
    public static User toEntity(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

}
