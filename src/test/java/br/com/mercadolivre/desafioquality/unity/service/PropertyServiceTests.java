package br.com.mercadolivre.desafioquality.unity.service;

import br.com.mercadolivre.desafioquality.exceptions.*;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.repository.PropertyRepository;
import br.com.mercadolivre.desafioquality.services.PropertyService;
import br.com.mercadolivre.desafioquality.services.validators.NeighborhoodValidationService;
import br.com.mercadolivre.desafioquality.utils.PropertyUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


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
    @DisplayName("Given a Property with valid data, when call calcPropertyPrice, then return the total price based on neighborhood and area")
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
        BigDecimal real = this.propertyService.getPropertyPrice(UUID.randomUUID());

        // ASSERT
        Assertions.assertEquals(expected, real);
    }

    @Test
    @DisplayName("Given an id as null, when call calcPropertyPrice, then throw an error containing \"O id é nulo!\"")
    public void testIfIdReceivedIsANullValue() {
        Exception thrown = Assertions.assertThrows(NullIdException.class, () -> this.propertyService.getPropertyPrice(null));

        Assertions.assertEquals("O id é nulo!", thrown.getMessage());
    }

    @Test
    @DisplayName("Given a empty property, when call calcPropertyArea, then throw an error containing \"Propriedade não encontrada\"")
    public void testIfRequestedPropertyNotFound() throws DatabaseReadException {
        // SETUP
        Mockito.when(this.propertyRepository.find(Mockito.any())).thenReturn(java.util.Optional.empty());

        // ACT
        Exception thrown = Assertions.assertThrows(
                PropertyNotFoundException.class, () -> this.propertyService.getPropertyPrice(UUID.randomUUID())
        );

        // ASSERT
        Assertions.assertEquals("Propriedade não encontrada", thrown.getMessage());
    }

    @Test
    @DisplayName("Given a property containing valid rooms, when call calcPropertyArea, then return the sum of the area of all rooms")
    public void testIfPropertyAreaIsCorrect() {
        Double expected = 190.35;
        Property fakeProperty = PropertyUtils.buildMockProperty();
        Double propertyArea = propertyService.calcPropertyArea(fakeProperty);

        Assertions.assertEquals(expected, propertyArea);
    }

    @Test
    @DisplayName("Given a property as null, when call calcPropertyArea, then throw an error containing \"Propriedade não encontrada\"")
    public void testIfReturnZeroWhenReceiveNullValue() {
        Exception thrown = Assertions.assertThrows(
                PropertyNotFoundException.class, () -> this.propertyService.calcPropertyArea(null)
        );

        Assertions.assertEquals("Propriedade não encontrada", thrown.getMessage());
    }

    @Test
    @DisplayName("Given a property to be created, when call addProperty, then return an not null id")
    public void testIfCreatedPropertyReceivesAnId() throws DatabaseWriteException, DbEntryAlreadyExists, DatabaseReadException, DatabaseManagementException {

        // SETUP
        Property fakeProperty = new Property();

        Mockito.when(this.propertyRepository.add(Mockito.any())).thenReturn(fakeProperty);

        // ACT
        propertyService.addProperty(fakeProperty);

        // ASSERT
        Assertions.assertNotNull(fakeProperty.getId());
    }

    @Test
    @DisplayName("Given a scenario that a property is not found, when call find method, then return an empty Property")
    public void testIfOnNotFoundPropertyIdOnDbItReturnsANewEmptyProperty() throws DatabaseReadException, DatabaseManagementException {
        // SETUP
        Mockito.when(this.propertyRepository.find(Mockito.any())).thenReturn(Optional.empty());

        // ACT
        Property emptyProperty = this.propertyService.findProperty(UUID.randomUUID());

        // ASSERT
        Assertions.assertTrue(
                Stream.of(emptyProperty.getPropDistrict(), emptyProperty.getPropName())
                        .allMatch(Objects::isNull));
    }

    @Test
    @DisplayName("Given a storage containing four valid properties, when call listProperties, then return all these properties")
    public void testIfListOfPropertyShoudBeAPropertyList() throws DatabaseReadException, DatabaseManagementException {
        //SETUP
        List<Property> propertiesFake = List.of(
                new Property(UUID.randomUUID(), "PropiedadeFake", "bairroFake", new ArrayList<Room>(), new BigDecimal(190.35)),
                new Property(UUID.randomUUID(), "PropiedadeFake", "bairroFake", new ArrayList<Room>(), new BigDecimal(190.35)),
                new Property(UUID.randomUUID(), "PropiedadeFake", "bairroFake", new ArrayList<Room>(), new BigDecimal(190.35)),
                new Property(UUID.randomUUID(), "PropiedadeFake", "bairroFake", new ArrayList<Room>(), new BigDecimal(190.35))
        );

        Mockito.when(this.propertyRepository.read()).thenReturn(propertiesFake);

        //ACT
        List<Property> propertiesFinded = propertyService.ListProperties();

        //ASSERT
        Assertions.assertEquals(propertiesFake,propertiesFinded);

    }
}
