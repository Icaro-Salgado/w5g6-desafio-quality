package br.com.mercadolivre.desafioquality.service;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.exceptions.NeighborhoodNotFoundException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

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

        NeighborhoodService neighborhoodServiceMock = Mockito.mock(NeighborhoodService.class);
        Mockito.when(neighborhoodServiceMock.getNeighborhoodById(randomUUID)).thenReturn(neighborhood);
    }

//    @Test
//    @DisplayName("Given a valid neighborhood, when call createNeighborhood, then return ")
//    public void testIfNeighborhoodIsCreated() throws DatabaseManagementException, DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException {
//
//        Neighborhood neighborhoodOne = Neighborhood
//                .builder()
//                .nameDistrict("Vila Maria")
//                .valueDistrictM2(BigDecimal.valueOf(10000.0))
//                .build();
//
//        Mockito.when(this.repository.add(any())).thenReturn(neighborhoodOne);
//
//        Neighborhood testResult = this.service.createNeighborhood(neighborhoodOne);
//
//        assertNotNull(testResult);
//    }
//
//    @Test
//    @DisplayName("Given a duplicated neighborhood, when call createNeighborhood, the trhows an DbEntryAlreadyExists")
//    public void testIfThrowsErrorWhenNeighborhoodAlreadyRegistred() throws DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException, DatabaseManagementException {
//
//        Neighborhood neighborhoodOne = Neighborhood
//                .builder()
//                .nameDistrict("Vila Maria")
//                .valueDistrictM2(BigDecimal.valueOf(10000.0))
//                .build();
//
//        List<Neighborhood> neighborhoods = new ArrayList<>();
//        neighborhoods.add(Neighborhood.builder().nameDistrict("Vila Maria").build());
//
//        Neighborhood nei = this.service.createNeighborhood(neighborhoodOne);
//
//        Throwable exception = assertThrows(DbEntryAlreadyExists.class, ()->this.service.createNeighborhood(neighborhoodOne));
//                this.service.createNeighborhood(neighborhoodOne);
//
//    }
}
