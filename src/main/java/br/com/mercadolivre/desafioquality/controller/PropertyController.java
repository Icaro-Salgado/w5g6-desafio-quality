package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.mapper.PropertyMapper;
import br.com.mercadolivre.desafioquality.dto.request.CreatePropertyDTO;
import br.com.mercadolivre.desafioquality.dto.request.UpdatePropertyDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyDetailDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyListDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.desafioquality.exceptions.*;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.services.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/property")
@AllArgsConstructor
public class PropertyController {

    final private PropertyService propertyService;

    @GetMapping("/")
    public ResponseEntity<List<PropertyListDTO>> requestPropertyList(UriComponentsBuilder uriBuilder)
            throws DatabaseReadException, DatabaseManagementException {

        List<Property> properties = propertyService.ListProperties();

        List<PropertyListDTO> response = PropertyListDTO.ToDtoList(properties, uriBuilder, "/api/v1/property");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/property-value/{propertyId}")
    public ResponseEntity<BigDecimal> requestPropertyValue(@PathVariable UUID propertyId) throws DatabaseReadException, DatabaseManagementException {
        BigDecimal propertyPrice = propertyService.getPropertyPrice(propertyId);

        return ResponseEntity.ok(propertyPrice);
    }

  
    @PostMapping
    public ResponseEntity createProperty(
            @RequestBody @Valid CreatePropertyDTO propertyToAddDTO,
            UriComponentsBuilder uriBuilder
    ) throws DatabaseManagementException, DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException, NeighborhoodNotFoundException {

        Property propertyToAdd = propertyToAddDTO.toModel();

        UUID addedPropertyId = propertyService.addProperty(propertyToAdd);

        URI uri = uriBuilder
                .path("/api/v1/property/{id}")
                .buildAndExpand(addedPropertyId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDetailDTO> findPropertyByID(@PathVariable UUID id) throws DatabaseReadException, DatabaseManagementException {
        Property foundedProperty = propertyService.findProperty(id);
        Room foundedPropertyRoom = new Room(); // TODO: encontrar o maior quarto da propriedade

        return ResponseEntity.ok(PropertyMapper.toPropertyDetailResponse(foundedProperty, foundedPropertyRoom));
    }

    @GetMapping("/property-area/{id}")
    public ResponseEntity<PropertyValueDTO> requestPropertyArea(@PathVariable UUID id) throws DatabaseReadException, DatabaseManagementException {

        Property property = propertyService.findProperty(id);

        Double totalArea = propertyService.calcPropertyArea(property);

        PropertyValueDTO propertyResponse = PropertyMapper.toPropertyResponseArea(property, totalArea);

        return ResponseEntity.status(HttpStatus.OK).body(propertyResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProperty(
            @PathVariable UUID id,
            @RequestBody UpdatePropertyDTO updatePropertyDTO,
            UriComponentsBuilder uriBuilder
    ) throws DatabaseManagementException, DatabaseWriteException, DatabaseReadException {
        propertyService.updateProperty(id, updatePropertyDTO.propName);
        URI uri = uriBuilder
                .path("/api/v1/property/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) throws DatabaseWriteException, DatabaseReadException {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

}
