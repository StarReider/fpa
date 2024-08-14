package org.robe.fpa.repository;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.User;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long id);
    long save(User user);
    List<User> findAll();
    void deleteUser(Long userId);
}
