package org.example.domain.port;

import org.example.domain.model.User;

import java.util.Optional;

public interface UserPersistencePort {

    User save(User user);
    Optional<User> findByEmail(String email);
}
