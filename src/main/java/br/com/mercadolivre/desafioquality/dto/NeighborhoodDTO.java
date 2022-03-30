package br.com.mercadolivre.desafioquality.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class NeighborhoodDTO {

    private UUID id;

    @NotEmpty(message = "O bairro não pode ficar vazio! \n")
    @Size(max = 45,message = "O comprimento do bairro não pode exceder 45 caracteres!\n")
    private String nameDistrict;

    @NotEmpty(message = "O valor do metro quadrado do bairro não pode ficar vazio!\n")
    @Digits(integer = 13,fraction = 2,message = "O valor do metro quadrado não pode exceder 13 digitos!\n")
    private BigDecimal valueDistrictM2;

}
