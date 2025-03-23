package org.example.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {
    private String number;
    private String citycode;
    private String contrycode;
}
