package br.com.mercadolivre.desafioquality.dto.response;

import br.com.mercadolivre.desafioquality.dto.RoomDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PropertyDetailDTO {
    UUID id;
    String propName, propDistrict;
    BigDecimal totalPrice;
    Double area;
    LargerRoomDTO largerRoom;
    List<RoomDTO> rooms;
}