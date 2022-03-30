package br.com.mercadolivre.defafioquality.service;

import br.com.mercadolivre.defafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.defafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.defafioquality.exceptions.NullIdException;
import br.com.mercadolivre.defafioquality.exceptions.PropertyNotFoundException;
import br.com.mercadolivre.defafioquality.models.Neighborhood;
import br.com.mercadolivre.defafioquality.models.Property;
import br.com.mercadolivre.defafioquality.models.Room;
import br.com.mercadolivre.defafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.defafioquality.repository.PropertyRepository;
import br.com.mercadolivre.defafioquality.services.PropertyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class PropertyServiceTests {

    private PropertyService propertyService;
    private NeighborhoodRepository neighborhoodRepository;

    // property service requirements
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setUp() {
        this.propertyRepository = Mockito.mock(PropertyRepository.class);
        this.neighborhoodRepository = Mockito.mock(NeighborhoodRepository.class);
        this.propertyService = new PropertyService(propertyRepository, neighborhoodRepository);

    }

    @AfterEach
    public void reset() {
        this.propertyRepository = null;
    }

    @Test
    public void testIfPropertyPriceIsCorrect() throws DatabaseReadException, DatabaseManagementException {
        // SETUP
        BigDecimal expected = BigDecimal.valueOf(3200);

        /// Setting up fake property
        List<Room> fakeRooms = List.of(new Room("Room fake", 8.0, 4.0));

        Property fakeProperty = new Property();
        fakeProperty.setPropRooms(fakeRooms);

        Mockito.when(this.propertyRepository.find(Mockito.any())).thenReturn(java.util.Optional.of(fakeProperty));

        /// Setting up fake neighborhood
        Neighborhood fakeNeighborhood = new Neighborhood();
        fakeNeighborhood.setValueDistrictM2(BigDecimal.valueOf(100));

        Mockito.when(this.neighborhoodRepository.find(Mockito.any())).thenReturn(java.util.Optional.of(fakeNeighborhood));

        // ACT
        BigDecimal real = this.propertyService.calcPropertyPrice(UUID.randomUUID());

        // ASSERT
        Assertions.assertEquals(expected, real);
    }

    @Test
    public void testIfIdRecivedIsANullValue() {
        Exception thrown = Assertions.assertThrows(NullIdException.class, () -> this.propertyService.calcPropertyPrice(null));

        Assertions.assertTrue(thrown.getMessage().equals("O id é nulo!"));
    }

    @Test
    public void testIfRequestedPropertyNotFound() throws DatabaseReadException {
        // SETUP
        Mockito.when(this.propertyRepository.find(Mockito.any())).thenReturn(java.util.Optional.empty());

        // ACT
        Exception thrown = Assertions.assertThrows(
                PropertyNotFoundException.class, () -> this.propertyService.calcPropertyPrice(UUID.randomUUID())
        );

        // ASSERT
        Assertions.assertTrue(thrown.getMessage().equals("Propriedade não encontrada"));
    }
}
