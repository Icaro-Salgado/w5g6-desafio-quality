package br.com.mercadolivre.desafioquality.dto.mapper;

import br.com.mercadolivre.desafioquality.dto.RoomDTO;
import br.com.mercadolivre.desafioquality.dto.response.LargerRoomDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyDetailDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;


public class PropertyMapper {

    public static PropertyValueDTO toPropertyResponseArea(Property property, Double totalArea) {

        PropertyValueDTO response = new PropertyValueDTO();

        response.setPropName(property.getPropName());
        response.setPropDistrict(property.getPropDistrict());
        response.setPropValue(property.getPropValue());
        response.setNumberOfRooms(property.getPropRooms().size());

        if (totalArea != null) {
            response.setTotalArea(totalArea);
        }

        return response;
    }

    public static PropertyDetailDTO toPropertyDetailResponse(Property property, Room largerRoom) {
        LargerRoomDTO largerRoomDTO = LargerRoomDTO
                .builder()
                .name(largerRoom.getRoomName())
                .totalArea(largerRoom.getRoomTotalArea())
                .width(largerRoom.getRoomWidth())
                .length(largerRoom.getRoomLength())
                .build();

        return PropertyDetailDTO
                .builder()
                .id(property.getId())
                .propName(property.getPropName())
                .propDistrict(property.getPropDistrict())
                .totalPrice(property.getPropValue())
                .area(property.getPropArea())
                .largerRoom(largerRoomDTO)
                .rooms(RoomDTO.fromModelList(property.getPropRooms()))
                .build();
    }
}
