package br.com.mercadolivre.defafioquality.service;

import br.com.mercadolivre.defafioquality.repository.PropertyRepository;
import br.com.mercadolivre.defafioquality.services.PropertyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;


public class PropertyServiceTests {

    private PropertyRepository propertyRepository;

    private PropertyService propertyService;

    @BeforeEach
    public void setUp() {
        this.propertyRepository = Mockito.mock(PropertyRepository.class);
        this.propertyService = new PropertyService(propertyRepository);
    }

    @AfterEach
    public void reset() {
        this.propertyRepository = null;
    }

    @Test
    public void testIfPropertyPriceIsCorrect() {
        BigDecimal expected = BigDecimal.valueOf(32);

        BigDecimal real = this.propertyService.calcPropertyPrice(UUID.randomUUID());

        Assertions.assertEquals(expected, real);
    }
}
