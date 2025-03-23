package org.example.infrastructure.output.mapper;

import org.example.domain.model.Phone;
import org.example.infrastructure.output.entities.PhoneEntity;
import org.springframework.stereotype.Component;

@Component
public class PhoneEntityMapper {

    public PhoneEntity toEntity(Phone domain) {
        if (domain == null) return null;

        PhoneEntity phone = new PhoneEntity();
        phone.setNumber(domain.getNumber());
        phone.setCitycode(domain.getCitycode());
        phone.setContrycode(domain.getContrycode());
        return phone;
    }

    public Phone toDomain(PhoneEntity entity) {
        if (entity == null) return null;

        return Phone.builder()
                .number(entity.getNumber())
                .citycode(entity.getCitycode())
                .contrycode(entity.getContrycode())
                .build();
    }
}
