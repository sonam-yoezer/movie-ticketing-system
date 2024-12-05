package com.java.movieticketingsystem.user.service;

import com.java.movieticketingsystem.auth.helper.UserInfoDetails;
import com.java.movieticketingsystem.user.mapper.UserMapper;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import com.java.movieticketingsystem.user.repository.UserRepository;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.java.movieticketingsystem.utils.constants.UserConstants.DELETED_SUCCESSFULLY_MESSAGE;
import static com.java.movieticketingsystem.utils.constants.UserConstants.DUPLICATE_EMAIL_MESSAGE;
import static com.java.movieticketingsystem.utils.constants.UserConstants.USER;
import static com.java.movieticketingsystem.utils.constants.UserConstants.NOT_FOUND_MESSAGE;
import static com.java.movieticketingsystem.utils.constants.UserConstants.UPDATED_SUCCESSFULLY_MESSAGE;

@Service
public class UserService implements IUserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return List.of();
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.userRepository.save(user);

        return UserMapper.toDTO(savedUser);

    }

    @Override
    public UserDTO findById(long id) throws Exception {
        return null;
    }

    @Override
    public String update(long id, User entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }


    @Override
    public UserDTO fetchSelfInfo() {
        return null;
    }
}
