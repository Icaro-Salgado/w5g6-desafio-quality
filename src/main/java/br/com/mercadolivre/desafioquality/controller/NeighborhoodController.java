package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.dto.mapper.PropertyMapper;
import br.com.mercadolivre.desafioquality.dto.response.PropertyResponseDTO;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Property;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/neighborhood")
@AllArgsConstructor
public class NeighborhoodController {

    @PostMapping("/")
    public ResponseEntity<List<Object>> postNeighborhood() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/")
    public ResponseEntity<List<Object>> requestNeighborhoodList() throws DatabaseReadException, DatabaseManagementException {

        return ResponseEntity.ok(new ArrayList<>());
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
