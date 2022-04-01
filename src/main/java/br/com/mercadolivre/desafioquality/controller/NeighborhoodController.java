package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.NeighborhoodDTO;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.exceptions.DbEntryAlreadyExists;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/neighborhood")
@AllArgsConstructor
public class NeighborhoodController {

    private NeighborhoodService neighborhoodService;

    @PostMapping("/")
    public ResponseEntity<Neighborhood> postNeighborhood(
            @RequestBody @Valid NeighborhoodDTO newNeighborhoodDTO,
            UriComponentsBuilder uriBuilder
    ) throws DatabaseReadException, DatabaseManagementException, DbEntryAlreadyExists, DatabaseWriteException {


        Neighborhood newNeighborhood = newNeighborhoodDTO.toModel();

        UUID addedNeighborhood = neighborhoodService.createNeighborhood(newNeighborhood);

        URI uri = uriBuilder
                .path("api/v1/neighborhood/{id}")
                .buildAndExpand(addedNeighborhood)
                .toUri();

        return ResponseEntity.created(uri).build();
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
