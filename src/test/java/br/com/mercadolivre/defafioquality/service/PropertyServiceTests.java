package br.com.mercadolivre.defafioquality.service;

import br.com.mercadolivre.defafioquality.repository.PropertyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;


public class PropertyServiceTests {

    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setUp() {
        this.propertyRepository = Mockito.mock(PropertyRepository.class);
    }

    @AfterEach
    public void reset() {
        this.propertyRepository = null;
    }
}
