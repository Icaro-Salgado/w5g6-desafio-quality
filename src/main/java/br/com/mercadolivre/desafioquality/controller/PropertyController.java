package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.mapper.PropertyMapper;
import br.com.mercadolivre.desafioquality.dto.request.PropertyDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyCreatedDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.desafioquality.exceptions.*;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.services.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/property")
@AllArgsConstructor
public class PropertyController {

    final private PropertyService propertyService;

    @GetMapping("/property-value/{propertyId}")
    public ResponseEntity<PropertyValueDTO> requestPropertyValue(@PathVariable UUID propertyId) throws DatabaseReadException, DatabaseManagementException {

        Property property = propertyService.calcPropertyPrice(propertyId);

        PropertyValueDTO propertyResponse = PropertyMapper.toPropertyResponse(property);

        return ResponseEntity.status(HttpStatus.OK).body(propertyResponse);
    }

    @PostMapping
    public ResponseEntity<PropertyCreatedDTO> createProperty(
            @RequestBody @Valid PropertyDTO propertyToAddDTO,
            UriComponentsBuilder uriBuilder
    ) throws DatabaseManagementException, DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException, NeighborhoodNotFoundException {

        Property propertyToAdd = propertyToAddDTO.toModel();

        Property addedProperty = propertyService.addProperty(propertyToAdd);

        URI uri = uriBuilder
                .path("/api/v1/property/{id}")
                .buildAndExpand(addedProperty.getId())
                .toUri();

        return ResponseEntity.created(uri).body(PropertyCreatedDTO.fromModel(addedProperty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> findPropertyByID(@PathVariable UUID id) throws DatabaseReadException, DatabaseManagementException {
        Property foundedProperty = propertyService.find(id);

        PropertyDTO foundedPropertyDTO = PropertyDTO
                .builder()
                .id(foundedProperty.getId())
                .propName(foundedProperty.getPropName())
                .propDistrict(foundedProperty.getPropDistrict())
                .propRooms(foundedProperty.getPropRooms())
                .build();

        return ResponseEntity.ok(foundedPropertyDTO);
    }
}
