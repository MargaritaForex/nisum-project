package org.example.infrastructure.input.http.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PhoneDTO {
    private String number;
    private String citycode;
    private String contrycode;
}
