package org.example.domain.port;

import org.example.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {

    User save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
