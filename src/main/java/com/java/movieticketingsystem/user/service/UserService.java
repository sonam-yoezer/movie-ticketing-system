package com.java.movieticketingsystem.user.service;

import com.java.movieticketingsystem.auth.helper.UserInfoDetails;
import com.java.movieticketingsystem.user.mapper.UserMapper;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import com.java.movieticketingsystem.user.model.UserUpdateDTO;
import com.java.movieticketingsystem.user.repository.UserRepository;
import com.java.movieticketingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.util.StringUtils;
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

import static com.java.movieticketingsystem.utils.constants.UserConstants.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserDTO> findAll() {
        List<User> user = this.userRepository.findAll();
        return UserMapper.toDTO(user);
    }
    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }
    @Override
    public UserDTO fetchById(long id) {
        User user = findById(id);
        return UserMapper.toDTO(user);
    }
@Override
public User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
    }


    @Override
    public User fetchSelfInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserInfoDetails) {
                UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();
                String email = userDetails.getUsername();
                return findByEmail(email).orElseThrow(
                        () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                                USER.toLowerCase())));
            }
            throw new GlobalExceptionWrapper.UnauthorizedException("User not authenticated");
        } catch (Exception e) {
            throw new GlobalExceptionWrapper.UnauthorizedException("Error fetching user information: " + e.getMessage());
        }
    }
    public Optional<User> findByEmail(@NonNull String emailId) {
        return this.userRepository.findByEmail(emailId);
    }
    @Override
    public String update(long id, @NonNull UserDTO userDTO) {
        // Get authenticated user
        User authenticatedUser = fetchSelfInfo();
        User userToUpdate;

        // Check if user is admin or updating their own profile
        if (Arrays.stream(authenticatedUser.getRoles().split(","))
                .anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            userToUpdate = findById(id);
        } else if (authenticatedUser.getId() != id) {
            throw new GlobalExceptionWrapper.UnauthorizedException("You can only update your own profile");
        } else {
            userToUpdate = authenticatedUser;
        }

        // Update fields if provided
        if (userDTO.getName() != null && !userDTO.getName().trim().isEmpty()) {
            userToUpdate.setName(userDTO.getName());
        }

        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().trim().isEmpty()) {
            userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if (userDTO.getEmail() != null && !userDTO.getEmail().trim().isEmpty()) {
            // Check if new email already exists for another user
            if (!userToUpdate.getEmail().equals(userDTO.getEmail()) &&
                    userRepository.existsByEmail(userDTO.getEmail())) {
                throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
            }
            userToUpdate.setEmail(userDTO.getEmail());
        }

        // Save the updated user
        userRepository.save(userToUpdate);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    public String update(long id, @NonNull UserUpdateDTO userUpdateDTO) {
        return "";
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        User authenticatedUser = fetchSelfInfo();
        //Allow to delete by admin to the user info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = findById(id);
        }
        this.userRepository.deleteById(authenticatedUser.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }
}
