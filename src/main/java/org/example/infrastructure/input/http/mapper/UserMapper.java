package org.example.infrastructure.input.http.mapper;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.Phone;
import org.example.domain.model.User;
import org.example.infrastructure.input.http.dto.request.PhoneDTO;
import org.example.infrastructure.input.http.dto.request.UserDTO;
import org.example.infrastructure.input.http.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PhoneMapper phoneMapper;

    public User toDomain(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // Asegúrate de encriptar la contraseña antes de guardarla
                .phones(getPhones(dto))
                .created(dto.getCreated())
                .modified(dto.getModified())
                .lastLogin(dto.getLastLogin())
                .isActive(dto.isActive())
                .token(dto.getToken())
                .build();
    }

    private List<Phone> getPhones(UserDTO dto) {
        return Optional.ofNullable(dto.getPhones())
                .map(getListListFunction())
                .orElse(Collections.emptyList());
    }

    private Function<List<PhoneDTO>, List<Phone>> getListListFunction() {
        return phones -> phones.stream()
                .map(phoneMapper::toDomain)
                .collect(Collectors.toList());
    }

    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phones(getPhones(user))
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .isActive(user.isActive())
                .token(user.getToken())
                .build();
    }

    private List<PhoneDTO> getPhones(User userResponse) {
        return Optional.ofNullable(userResponse.getPhones())
                .map(getListFunction())
                .orElseGet(Collections::emptyList);
    }

    private Function<List<Phone>, List<PhoneDTO>> getListFunction() {
        return phones -> phones.stream().map(phoneMapper::toDto).toList();
    }

}
