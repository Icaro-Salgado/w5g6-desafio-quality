package br.com.mercadolivre.desafioquality.dto.response;

import br.com.mercadolivre.desafioquality.models.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDTO {

    private UUID id;
    private String propName;
    private String propDistrict;
    private List<Room> propRooms;
    private BigDecimal propValue;
}
