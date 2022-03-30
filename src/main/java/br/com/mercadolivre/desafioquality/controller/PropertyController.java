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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PropertyController {

    final private PropertyService propertyService;

    @GetMapping("/property-value/{propertyId}")
    public ResponseEntity<PropertyValueDTO> requestPropertyValue(@PathVariable UUID propertyId) throws DatabaseReadException, DatabaseManagementException {

        Property property = propertyService.calcPropertyPrice(propertyId);

        PropertyValueDTO propertyResponse = PropertyMapper.toPropertyResponse(property);

        return ResponseEntity.status(HttpStatus.OK).body(propertyResponse);
    }
}
