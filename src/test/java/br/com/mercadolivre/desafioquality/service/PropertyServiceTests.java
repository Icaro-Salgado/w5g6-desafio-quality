package br.com.mercadolivre.desafioquality.service;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.NullIdException;
import br.com.mercadolivre.desafioquality.exceptions.PropertyNotFoundException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.repository.PropertyRepository;
import br.com.mercadolivre.desafioquality.services.PropertyService;

import br.com.mercadolivre.desafioquality.services.validators.NeighborhoodValidationService;
import br.com.mercadolivre.desafioquality.utils.PropertyUtils;
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
    private NeighborhoodValidationService neighborhoodValidationService;

    @BeforeEach
    public void setUp() {
        this.propertyRepository = Mockito.mock(PropertyRepository.class);
        this.neighborhoodRepository = Mockito.mock(NeighborhoodRepository.class);
        this.neighborhoodValidationService = Mockito.mock(NeighborhoodValidationService.class);

        this.propertyService = new PropertyService(propertyRepository, neighborhoodRepository, neighborhoodValidationService);

    }

    @AfterEach
    public void reset() {
        this.propertyRepository = null;
    }

    @Test
    public void testIfPropertyPriceIsCorrect() throws DatabaseReadException, DatabaseManagementException {
        // SETUP
        BigDecimal expected = new BigDecimal("19035.00");

        /// Setting up fake neighborhood
        Neighborhood fakeNeighborhood = new Neighborhood();
        fakeNeighborhood.setValueDistrictM2(BigDecimal.valueOf(100));

        String fakeNeighborhoodName = "Fake neighborhood";
        fakeNeighborhood.setNameDistrict(fakeNeighborhoodName);


        Property fakeProperty = PropertyUtils.buildMockProperty();

        /// Teaching Mockito
        Mockito.when(this.propertyRepository.find(Mockito.any())).thenReturn(java.util.Optional.of(fakeProperty));
        Mockito.when(this.neighborhoodRepository.read()).thenReturn(List.of(fakeNeighborhood));

        // ACT
        BigDecimal real = this.propertyService.calcPropertyPrice(UUID.randomUUID()).getPropValue();

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

    @Test
    public void testIfPropertyAreaIsCorrect(){
        Double expected = 190.35;

        Property fakeProperty = PropertyUtils.buildMockProperty();

        Double propertyArea = propertyService.calcPropertyArea(fakeProperty);

        Assertions.assertEquals(expected, propertyArea);
    }

    @Test
    public void testIfReturnZeroWhenReceiveNullValue(){
        Double expected = 0.0;

        Double propertyArea = propertyService.calcPropertyArea(null);

        Assertions.assertEquals(expected, propertyArea);
    }
}
