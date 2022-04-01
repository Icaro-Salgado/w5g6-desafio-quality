package br.com.mercadolivre.desafioquality.unity.service;

import br.com.mercadolivre.desafioquality.exceptions.*;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class NeighborhoodServiceTests {

    private NeighborhoodRepository neighborhoodRepository;
    private NeighborhoodService neighborhoodService;

    @BeforeEach
    public void setUp() {
        this.neighborhoodRepository = Mockito.mock(NeighborhoodRepository.class);
        this.neighborhoodService = new NeighborhoodService(this.neighborhoodRepository);
    }

    @Test
    @DisplayName("Given an existing neighborhood, when call find method with UUID from that neighborhood, then return this one")
    public void testFindNeighborhoodFromValidUUID() throws DatabaseReadException {
        Neighborhood neighborhood = new Neighborhood();
        UUID randomUUID = UUID.randomUUID();
        neighborhood.setId(randomUUID);
        Mockito.when(neighborhoodRepository.find(Mockito.any())).thenReturn(Optional.of(neighborhood));

        Neighborhood foundNeighborhood = neighborhoodService.getNeighborhoodById(randomUUID);

        Assertions.assertEquals(foundNeighborhood.getId(), neighborhood.getId());
    }

    @Test
    @DisplayName("Given an UUID from a non existing neighborhood, when call find method, then throw an error containing \"Bairro não encontrado\"")
    public void testFindNonExistingNeighborhoodFromRandomUUID() {
        Exception thrown = Assertions.assertThrows(
                NeighborhoodNotFoundException.class,
                () -> neighborhoodService.getNeighborhoodById(UUID.fromString("2999537f-11dc-40c1-a834-7249f2a361dd"))
        );

        Assertions.assertEquals("Bairro não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Given an neighborhood id, when call delete method, then getNeighborhoodById must be called once")
    public void testDeleteMethodWasCalled() throws DatabaseReadException, DatabaseWriteException {
        Neighborhood neighborhood = new Neighborhood();
        UUID randomUUID = UUID.randomUUID();
        neighborhood.setId(randomUUID);

        Mockito.when(neighborhoodRepository.find(Mockito.any())).thenReturn(Optional.of(neighborhood));
        neighborhoodService.deleteNeighborhoodById(randomUUID);

        Mockito.verify(neighborhoodRepository, Mockito.times(1)).delete(neighborhood);
    }

    @Test
    @DisplayName("Given a valid neighborhood, when call createNeighborhood, then createNeighborhood must be called once")
    public void testIfNeighborhoodIsCreated() throws DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException {

        Neighborhood neighborhoodOne = Neighborhood
                .builder()
                .id(UUID.randomUUID())
                .nameDistrict("Vila Maria")
                .valueDistrictM2(BigDecimal.valueOf(10000.0))
                .build();

        Mockito.when(this.neighborhoodRepository.add(any())).thenReturn(neighborhoodOne);

        UUID testResult = this.neighborhoodService.createNeighborhood(neighborhoodOne);

        Mockito.verify(neighborhoodRepository, Mockito.times(1)).add(neighborhoodOne);

        Assertions.assertEquals(testResult, neighborhoodOne.getId());
    }
}
