package org.example.infrastructure.input.http.mapper;

import org.example.domain.model.Phone;
import org.example.infrastructure.input.http.dto.request.PhoneDTO;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {

    public Phone toDomain(PhoneDTO dto) {
        if (dto == null) return null;

       return Phone.builder()
                .number(dto.getNumber())
                .citycode(dto.getCitycode())
                .contrycode(dto.getContrycode())
                .build();
    }

    public PhoneDTO toDto(Phone domain) {
        if (domain == null) return null;

        return PhoneDTO.builder()
                .number(domain.getNumber())
                .citycode(domain.getCitycode())
                .contrycode(domain.getContrycode())
                .build();
    }
}
