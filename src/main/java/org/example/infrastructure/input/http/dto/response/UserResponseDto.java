package org.example.infrastructure.input.http.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.infrastructure.input.http.dto.request.PhoneDTO;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
    private String token;
    private boolean isActive;
    private String created;
    private String modified;
    private String lastLogin;
}
