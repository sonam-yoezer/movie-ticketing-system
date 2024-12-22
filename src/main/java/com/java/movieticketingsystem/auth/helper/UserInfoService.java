package com.java.movieticketingsystem.auth.helper;

import com.java.movieticketingsystem.user.model.User;
import com.java.movieticketingsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username) // Assuming email is used as the username
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (!user.isEnabled()) {
            throw new DisabledException("User account is disabled: " + username);
        }

        return new UserInfoDetails(user);
    }
}
