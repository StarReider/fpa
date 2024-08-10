package org.robe.fpa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.User;
import org.robe.fpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired(required = false)
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return null;//userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return null;//userRepository.findById(userId);
    }
    
    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return null;//userRepository.save(user);
    }

    public User updateUser(Long userId, User userDetails) {
        Optional<User> userOptional = null;//userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setPasswordHash(userDetails.getPasswordHash());
            user.setEmail(userDetails.getEmail());
            user.setUpdatedAt(LocalDateTime.now());
            return null;//userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        //userRepository.deleteById(userId);
    }
}
