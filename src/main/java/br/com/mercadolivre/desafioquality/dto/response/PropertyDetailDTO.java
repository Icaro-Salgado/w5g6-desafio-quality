package br.com.mercadolivre.desafioquality.dto.response;

import br.com.mercadolivre.desafioquality.dto.RoomDTO;
import br.com.mercadolivre.desafioquality.models.Property;
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

    public static PropertyDetailDTO fromModel(Property property) {
        return PropertyDetailDTO
                .builder()
                .id(property.getId())
                .propName(property.getPropName())
                .propDistrict(property.getPropDistrict())
                .totalPrice(property.getPropValue())
                .area(property.getPropArea())
                .largerRoom(LargerRoomDTO.builder().build())
                .rooms(RoomDTO.fromModelList(property.getPropRooms()))
                .build();
    }
}