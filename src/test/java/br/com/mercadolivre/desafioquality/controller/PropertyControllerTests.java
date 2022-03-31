package br.com.mercadolivre.desafioquality.controller;

import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.repository.ApplicationRepository;
import br.com.mercadolivre.desafioquality.repository.PropertyRepository;
import br.com.mercadolivre.desafioquality.utils.DatabaseUtils;
import br.com.mercadolivre.desafioquality.utils.PropertyUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class PropertyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseUtils databaseUtils;


    @AfterAll
    public void afterAll(){
        databaseUtils.deleteDatabase();
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/property-area/{propertyId}")
    public void testCalculatePropertyArea() throws Exception {
        PropertyUtils.buildMockProperty();
        ApplicationRepository<Property, UUID> mockPropertyRepo = Mockito.mock(PropertyRepository.class);
        Mockito.when(mockPropertyRepo.find(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/property-area/{propertyId}", "77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalArea").value(190.35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.propDistrict").value("Fake neighborhood"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfRooms").value(3));
    }
}