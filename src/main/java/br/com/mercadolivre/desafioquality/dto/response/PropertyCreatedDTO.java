package br.com.mercadolivre.desafioquality.dto.response;

import br.com.mercadolivre.desafioquality.models.Property;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PropertyCreatedDTO {

    private UUID id;
    private String propName;
    private String propDistrict;
    private BigDecimal propValue;


    public static PropertyCreatedDTO fromModel(Property model) {
        return PropertyCreatedDTO
                .builder()
                .id(model.getId())
                .propName(model.getPropName())
                .propDistrict(model.getPropDistrict())
                .propValue(model.getPropValue())
                .build();
    }
}
