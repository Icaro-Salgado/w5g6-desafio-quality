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
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Property {

    private UUID id;
    private String propName;
    private String propDistrict;
    private List<Room> propRooms;
    private BigDecimal propValue;
    private Double propArea;
    private UUID largestRoomId;

    public Property(UUID id, String propName, String propDistrict, List<Room> propRooms, BigDecimal propValue) {
        this.id = id;
        this.propName = propName;
        this.propDistrict = propDistrict;
        this.propRooms = propRooms;
        this.propValue = propValue;
    }
}
