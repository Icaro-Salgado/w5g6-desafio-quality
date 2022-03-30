package br.com.mercadolivre.desafioquality.service.validators;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import br.com.mercadolivre.desafioquality.services.validators.NeighborhoodValidationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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
    public void testIfPassesForAllValidations() throws DatabaseReadException, DatabaseManagementException {
        // SETUP
        List<Neighborhood> fakeNeighborhoods = new ArrayList<>();
        fakeNeighborhoods.add(Neighborhood.builder().nameDistrict("Não é fake").build());

        Mockito.when(this.neighborhoodRepository.read()).thenReturn(fakeNeighborhoods);

        // ACT
        this.neighborhoodValidationService.validate("Não é fake");

        // ASSERT
        Mockito.verify(this.neighborhoodRepository, Mockito.times(1)).read();
    }
}
