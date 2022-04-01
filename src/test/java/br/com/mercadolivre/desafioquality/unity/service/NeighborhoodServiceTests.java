package br.com.mercadolivre.desafioquality.unity.service;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import br.com.mercadolivre.desafioquality.test_utils.NeighborhoodUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.exceptions.NeighborhoodNotFoundException;
import br.com.mercadolivre.desafioquality.exceptions.*;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import java.security.InvalidParameterException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class NeighborhoodServiceTests {

    @Mock
    private NeighborhoodRepository neighborhoodRepository;

    @InjectMocks
    private NeighborhoodService neighborhoodService;

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
    @DisplayName("Given a page and limit, when call listNeighborhood, then return all data from calculated offset")
    public void testIfReturnAllWithLimitProperties() throws DatabaseReadException {
        List<Neighborhood> expectedList = NeighborhoodUtils.getMockList(10);

        Mockito.when(neighborhoodRepository.read(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expectedList);

        List<Neighborhood> result = neighborhoodService.listNeighborhood(1, 4);

        assertEquals(expectedList, result);
        Mockito.verify(neighborhoodRepository, Mockito.times(1)).read(0, 4);
    }

    @Test
    @DisplayName("Given null page and limit, when call listNeighborhood, should throw an exception")
    public void testIfReturnExceptionWhenReceiveNullAsParameters() throws DatabaseReadException {

        assertThrows(InvalidParameterException.class, () -> neighborhoodService.listNeighborhood(null, null));

        Mockito.verify(neighborhoodRepository, Mockito.times(0)).read(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Given negative page and limit, when call listNeighborhood, should throw an exception")
    public void testIfThrowExceptionIfReceiveInvalidLimit() throws DatabaseReadException {
        assertThrows(InvalidParameterException.class, () -> neighborhoodService.listNeighborhood(1, -1));
        Mockito.verify(neighborhoodRepository, Mockito.times(0)).read(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Given a limit, when call getTotalPages, should return total pages with results")
    public void testIfReturnPageNumbersCorrectly() throws DatabaseReadException {
        Integer expected = 2;

        Mockito.when(neighborhoodRepository.read()).thenReturn(NeighborhoodUtils.getMockList(5));

        Integer pages = neighborhoodService.getTotalPages(4);

        assertEquals(expected, pages);
    }

    @Test
    @DisplayName("Given null as limit, when call getTotalPages, should return zero")
    public void testIfReturnZeroWhenReceiveNullAsLimit() throws DatabaseReadException {
        Integer expected = 0;
        Integer pages = neighborhoodService.getTotalPages(null);

        assertEquals(expected, pages);
        Mockito.verify(neighborhoodRepository, Mockito.times(0)).read();
    }

    @Test
    @DisplayName("Given negative number as limit, when call getTotalPages, should return zero")
    public void testIfReturnZeroWhenReceiveInvalidLimit() throws DatabaseReadException {
        Integer expected = 0;
        Integer pages = neighborhoodService.getTotalPages(-4);

        assertEquals(expected, pages);
        Mockito.verify(neighborhoodRepository, Mockito.times(0)).read();
    }

    @Test
    @DisplayName("Given a valid neighborhood, when call createNeighborhood, then createNeighborhood must be called once")
    public void testIfNeighborhoodIsCreated() throws DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException {

        Neighborhood neighborhoodOne = Neighborhood
                .builder()
                .nameDistrict("Vila Maria")
                .valueDistrictM2(BigDecimal.valueOf(10000.0))
                .build();

        Mockito.when(this.neighborhoodRepository.add(any())).thenReturn(neighborhoodOne);

        Neighborhood testResult = this.neighborhoodService.createNeighborhood(neighborhoodOne);

        Mockito.verify(neighborhoodRepository, Mockito.times(1)).add(neighborhoodOne);
    }
}
