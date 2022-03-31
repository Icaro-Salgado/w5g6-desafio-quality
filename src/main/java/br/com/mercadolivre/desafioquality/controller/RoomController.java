package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.RoomDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.services.PropertyService;
import br.com.mercadolivre.desafioquality.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/room")
@AllArgsConstructor
public class RoomController {

    final private PropertyService propertyService;

    final private RoomService roomService;

    @GetMapping("biggest-room/{propertyId}")
    public ResponseEntity<RoomDTO> findBiggestRoom(@PathVariable UUID propertyId) throws DatabaseReadException, DatabaseManagementException {
        Property property = propertyService.findProperty(propertyId);
        List<Room> rooms = property.getPropRooms();
        Room biggest = roomService.getBiggestRoom(rooms);
        RoomDTO response = RoomDTO.fromModel(biggest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
