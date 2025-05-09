package com.online.store.users;

import com.online.store.entity.User;
import com.online.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagement {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Registers a new user with a password.
     *
     * @param username the username
     * @param password the password
     * @return the registered user
     */
    public User signUp(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        return userRepository.save(user);
    }

    /**
     * Authenticates a user by verifying the password.
     *
     * @param username the username
     * @param password the password
     * @return true if authentication is successful, false otherwise
     */
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        return password.equals(user.getPassword());
    }
}