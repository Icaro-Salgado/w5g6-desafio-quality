package br.com.mercadolivre.desafioquality.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Neighborhood {

    private UUID id;
    private String nameDistrict;
    private BigDecimal valueDistrictM2;

}
