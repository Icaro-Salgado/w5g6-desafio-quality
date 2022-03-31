package br.com.mercadolivre.desafioquality.service.validators;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.NeighborhoodNotFoundException;
import br.com.mercadolivre.desafioquality.exceptions.PropertyNotFoundException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.services.validators.NeighborhoodValidationService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NeighborhoodValidationServiceTests {
    private NeighborhoodValidationService neighborhoodValidationService;
    private NeighborhoodRepository neighborhoodRepository;

    @BeforeEach
    public void setUp() {
        this.neighborhoodRepository = Mockito.mock(NeighborhoodRepository.class);

        this.neighborhoodValidationService = new NeighborhoodValidationService(neighborhoodRepository);
    }

    @AfterEach
    public void reset() {
        this.neighborhoodValidationService = null;
    }

    @Test
    @DisplayName("Given a String containing an existing Neighborhood, when call validate method, then return true")
    public void testIfNeighborhoodIsValid() throws DatabaseReadException, DatabaseManagementException {
        // SETUP
        List<Neighborhood> fakeNeighborhoods = new ArrayList<>();
        fakeNeighborhoods.add(Neighborhood.builder().nameDistrict("Não é fake").build());
        Mockito.when(this.neighborhoodRepository.read()).thenReturn(fakeNeighborhoods);

        // ACT
        boolean neighborhoodExists = this.neighborhoodValidationService.validate("Não é fake");

        // ASSERT
        Assertions.assertTrue(neighborhoodExists);
    }

    @Test
    @DisplayName("Given a String of non existing Neighborhood, when call validate method, then throw an error containing \"Bairro não encontrado\"")
    public void testIfNeighborhoodIsNotValid() throws DatabaseReadException, DatabaseManagementException {
        // SETUP
        List<Neighborhood> fakeNeighborhoods = new ArrayList<>();
        Mockito.when(this.neighborhoodRepository.read()).thenReturn(fakeNeighborhoods);

        // ACT
        Exception thrown = Assertions.assertThrows(
                NeighborhoodNotFoundException.class, () -> this.neighborhoodValidationService.validate("É fake")
        );

        // ASSERT
        Assertions.assertEquals("Bairro não encontrado", thrown.getMessage());
    }
}
