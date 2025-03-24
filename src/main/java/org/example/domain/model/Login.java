package org.example.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Login {
    private String email;
    private String password;
}
