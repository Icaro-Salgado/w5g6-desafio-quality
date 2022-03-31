package br.com.mercadolivre.desafioquality.service;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.services.NeighborhoodService;
import br.com.mercadolivre.desafioquality.utils.NeighborhoodUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NeighborhoodServiceTests {

    @Mock
    private NeighborhoodRepository neighborhoodRepository;

    @InjectMocks
    private NeighborhoodService neighborhoodService;

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


}
