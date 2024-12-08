package com.java.movieticketingsystem.user.service;

import com.java.movieticketingsystem.auth.helper.UserInfoDetails;
import com.java.movieticketingsystem.user.mapper.UserMapper;
import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.model.UserDTO;
import com.java.movieticketingsystem.user.model.UserUpdateDTO;
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
    public List<UserDTO> findAll() {
        List<User> user = this.userRepository.findAll();
        return UserMapper.toDTO(user);
    }


    @Override
    public UserDTO findById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
        return UserMapper.toDTO(user);
    }

    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    public Optional<UserDTO> findByEmail(@NonNull String emailId) {
        Optional<User> user = this.userRepository.findByEmail(emailId);
        return UserMapper.toDTO(user);
    }

    @Override
    public String update(long id, @NonNull User entity) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow update by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            userEntity = UserMapper.toEntity(findById(id));
        }

        userEntity.setName(entity.getName());
        userEntity.setPhoneNumber(entity.getPhoneNumber());

        this.userRepository.save(userEntity);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow to delete by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            userEntity = UserMapper.toEntity(findById(id));
        }

        this.userRepository.deleteById(userEntity.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }


    @Override
    public String updatePartial(long id, UserUpdateDTO updateDTO) {
        // Validate that at least one field is present
        if (!updateDTO.hasAtLeastOneField()) {
            throw new GlobalExceptionWrapper.BadRequestException("At least one field must be provided for update");
        }

        // Get authenticated user
        UserDTO authenticatedUser = fetchSelfInfo();

        // Check if user has permission to update (must be admin or self)
        boolean isAdmin = Arrays.stream(authenticatedUser.getRoles().split(","))
                .anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"));
        boolean isSelf = authenticatedUser.getId() == id;

        if (!isAdmin && !isSelf) {
            throw new GlobalExceptionWrapper.UnauthorizedException("You don't have permission to update this user");
        }

        // Fetch user to update
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(
                        String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));

        // Apply updates only for non-null fields
        if (updateDTO.getName() != null) {
            userToUpdate.setName(updateDTO.getName());
        }
        if (updateDTO.getPhoneNumber() != null) {
            userToUpdate.setPhoneNumber(updateDTO.getPhoneNumber());
        }
        if (updateDTO.getEmail() != null) {
            // Check if new email already exists (skip check if email unchanged)
            if (!updateDTO.getEmail().equals(userToUpdate.getEmail()) &&
                    userRepository.existsByEmail(updateDTO.getEmail())) {
                throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
            }
            userToUpdate.setEmail(updateDTO.getEmail());
        }

        // Save updates
        userRepository.save(userToUpdate);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }
}
