package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.mapper.PropertyMapper;
import br.com.mercadolivre.desafioquality.dto.response.PropertyResponseDTO;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/neighborhood")
@AllArgsConstructor
public class NeighborhoodController {

    private NeighborhoodService neighborhoodService;

    @PostMapping("/")
    public ResponseEntity<List<Object>> postNeighborhood() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/")
    public ResponseEntity<List<Object>> requestNeighborhoodList() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Neighborhood> requestNeighborhoodById(@PathVariable UUID id) throws DatabaseReadException {
        return ResponseEntity.ok(neighborhoodService.getNeighborhoodById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNeighborhoodById(@PathVariable UUID id) throws DatabaseReadException, DatabaseWriteException {
        neighborhoodService.deleteNeighborhoodById(id);
        return ResponseEntity.noContent().build();
    }
}
