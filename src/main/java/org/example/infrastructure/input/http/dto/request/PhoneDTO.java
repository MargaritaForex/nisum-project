package org.example.infrastructure.input.http.dto.request;

import lombok.Data;

@Data
public class PhoneDTO {
    private String number;
    private String citycode;
    private String contrycode;
}
