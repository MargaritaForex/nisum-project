package org.example.infrastructure.input.http.mapper;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.Login;
import org.example.infrastructure.input.http.dto.request.LoginDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMapper {

    public Login toDomain(LoginDTO dto) {
        if (dto == null) return null;

        return Login.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

}
