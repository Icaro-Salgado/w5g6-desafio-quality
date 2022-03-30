package br.com.mercadolivre.desafioquality.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    private UUID id;
    private String propName;
    private String propDistrict;
    private List<Room> propRooms;
    private BigDecimal propValue;

}
