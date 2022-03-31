package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.response.NeighborhoodListDTO;
import br.com.mercadolivre.desafioquality.dto.response.NeighborhoodListItemDTO;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/neighborhood")
@AllArgsConstructor
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    @PostMapping("/")
    public ResponseEntity<List<Object>> postNeighborhood() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/")
    public ResponseEntity<NeighborhoodListDTO> requestNeighborhoodList(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            UriComponentsBuilder uriBuilder) throws DatabaseReadException {

        List<Neighborhood> neighborhoodList = neighborhoodService.listNeighborhood(page, size);

        int totalPages = neighborhoodService.getTotalPages(size);

        URI nextPageURI = page < totalPages ? uriBuilder
                .replacePath("/api/v1/neighborhood")
                .replaceQueryParam("page", page + 1)
                .replaceQueryParam("size", size)
                .build().toUri() : URI.create("");



        return ResponseEntity.ok(NeighborhoodListDTO.modelToDTO(
                NeighborhoodListItemDTO.modelToDTO(neighborhoodList, uriBuilder),
                page,
                neighborhoodService.getTotalPages(size),
                nextPageURI
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Object>> requestNeighborhoodById() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Object>> deleteNeighborhoodById() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
    }
}
