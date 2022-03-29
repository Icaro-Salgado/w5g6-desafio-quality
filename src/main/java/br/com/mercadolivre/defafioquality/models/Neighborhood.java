package br.com.mercadolivre.defafioquality.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Neighborhood {

    private UUID id;
    private String nameDistrict;
    private BigDecimal valueDistrictM2;

}
