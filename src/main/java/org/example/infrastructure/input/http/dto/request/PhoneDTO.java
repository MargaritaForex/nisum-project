package org.example.infrastructure.input.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "\\d{7,15}", message = "El número de teléfono debe contener entre 7 y 15 dígitos")
    private String number;

    @NotBlank(message = "El código de ciudad es obligatorio")
    @Pattern(regexp = "\\d{1,5}", message = "El código de ciudad debe contener entre 1 y 5 dígitos")
    private String citycode;

    @NotBlank(message = "El código de país es obligatorio")
    @Pattern(regexp = "\\d{1,5}", message = "El código de país debe contener entre 1 y 5 dígitos")
    private String contrycode;
}
