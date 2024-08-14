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
    
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    public long createUser(User user) {
        return userRepository.save(user);
    }

    public boolean updateUser(Long userId, User userDetails) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setPasswordHash(userDetails.getPasswordHash());
            user.setEmail(userDetails.getEmail());
            user.setUpdatedAt(LocalDateTime.now());
            
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }
}
