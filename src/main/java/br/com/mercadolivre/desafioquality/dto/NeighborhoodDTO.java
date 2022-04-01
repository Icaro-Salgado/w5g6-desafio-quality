package br.com.mercadolivre.desafioquality.dto;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class NeighborhoodDTO {

    private UUID id;

    @NotEmpty(message = "O bairro não pode ficar vazio!")
    @Size(max=45, message = "O comprimento do bairro não pode exceder 45 caracteres!")
    private String nameDistrict;

    @NotNull(message = "O valor do metro quadrado do bairro não pode ficar vazio!")
    @DecimalMin(value="0.0", inclusive = false, message = "O valor do metro quadrado do bairro não pode ser menor ou igual a zero!")
    @Digits(integer = 13,fraction = 2,message = "O valor do metro quadrado não pode exceder 13 digitos!")
    private BigDecimal valueDistrictM2;

    public Neighborhood toModel() {

        return Neighborhood.builder()
                .nameDistrict(this.nameDistrict)
                .valueDistrictM2(this.valueDistrictM2)
                .build();
    }
}