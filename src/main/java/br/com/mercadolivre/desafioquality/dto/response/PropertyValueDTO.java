package br.com.mercadolivre.desafioquality.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyValueDTO {

    private String propName;
    private String propDistrict;
    private BigDecimal propValue;

}
