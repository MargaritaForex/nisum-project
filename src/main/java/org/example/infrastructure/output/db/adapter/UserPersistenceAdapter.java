package org.example.infrastructure.output.db.adapter;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.User;
import org.example.domain.port.UserPersistencePort;
import org.example.infrastructure.output.db.UserRepository;
import org.example.infrastructure.output.entities.UserEntity;
import org.example.infrastructure.output.mapper.UserEntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter  implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        return userEntityMapper.toDomain(userRepository.save(userEntity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toDomain);
    }
}
