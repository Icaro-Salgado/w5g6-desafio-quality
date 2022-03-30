package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.mapper.PropertyMapper;
import br.com.mercadolivre.desafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.services.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/property-area/{propertyId}")
    public ResponseEntity<PropertyValueDTO> requestPropertyArea(@PathVariable UUID propertyId) throws DatabaseReadException, DatabaseManagementException {

        Property property = propertyService.findProperty(propertyId);

        Double totalArea = propertyService.calcPropertyArea(property);

        PropertyValueDTO propertyResponse = PropertyMapper.toPropertyResponseArea(property, totalArea);

        return ResponseEntity.status(HttpStatus.OK).body(propertyResponse);
    }
}
