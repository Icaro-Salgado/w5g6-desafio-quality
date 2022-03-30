package br.com.mercadolivre.desafioquality.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    private UUID id;
    private String propName;
    private String propDistrict;
    private List<Room> propRooms;
    private BigDecimal propValue;

}
