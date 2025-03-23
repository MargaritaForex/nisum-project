package org.example.infrastructure.output.mapper;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.Phone;
import org.example.domain.model.User;
import org.example.infrastructure.output.entities.PhoneEntity;
import org.example.infrastructure.output.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final PhoneEntityMapper phoneEntityMapper;

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword()) // Asegúrate de guardar la contraseña encriptada
                .phones(getPhones(user)) // Aquí quizás necesites un sub-mapper para teléfonos
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .isActive(user.isActive())
                .token(user.getToken())
                .build();
    }

    private List<PhoneEntity> getPhones(User user) {
        return Optional.ofNullable(user.getPhones())
                .map(getListFunction())
                .orElseGet(Collections::emptyList);
    }

    private Function<List<Phone>, List<PhoneEntity>> getListFunction() {
        return phones -> phones.stream().map(phoneEntityMapper::toEntity).toList();
    }

    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .phones(getPhones(entity)) // Verifica que sea compatible con el domain model
                .created(entity.getCreated())
                .modified(entity.getModified())
                .lastLogin(entity.getLastLogin())
                .isActive(entity.isActive())
                .token(entity.getToken())
                .build();
    }

    private List<Phone> getPhones(UserEntity user) {
        return Optional.ofNullable(user.getPhones())
                .map(getListFunctionTo())
                .orElseGet(Collections::emptyList);
    }

    private Function<List<PhoneEntity>, List<Phone>> getListFunctionTo() {
        return phones -> phones.stream().map(phoneEntityMapper::toDomain).toList();
    }
}
