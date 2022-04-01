package br.com.mercadolivre.desafioquality.integration;

import br.com.mercadolivre.desafioquality.dto.response.PropertyListDTO;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.test_utils.DatabaseUtils;
import br.com.mercadolivre.desafioquality.test_utils.PropertyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class PropertyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseUtils<Property[]> databaseUtils;

    private String propertyFile = "property.json";
    private String neighborhoodsFile = "neighborhood.json";

    @BeforeAll
    public void setUp() {
        databaseUtils.startDatabase(propertyFile);
        databaseUtils.startDatabase(neighborhoodsFile);
    }

    @BeforeEach
    public void reset() throws IOException {
        databaseUtils.resetDatabase(propertyFile);
        databaseUtils.resetDatabase(neighborhoodsFile);
    }

    @AfterAll
    public void closeTests() {
        databaseUtils.deleteDatabase();
    }

    private void populateFakeDatabase() throws IOException {
        databaseUtils.writeIntoFile(neighborhoodsFile, PropertyUtils.getFakeNeighborhoods());
        databaseUtils.writeIntoFile(propertyFile, PropertyUtils.getFakeProperties());
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/property-area/{propertyId}")
    public void testCalculatePropertyArea() throws Exception {
        // SETUP
        this.populateFakeDatabase();
        Property fakeProperty = PropertyUtils.getFakeProperties().get(0);

        // ACT
        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/property-area/{propertyId}", fakeProperty.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalArea").value(fakeProperty.getPropArea()));
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/")
    public void testListProperties() throws Exception {
        // SETUP
        this.populateFakeDatabase();

        List<Property> fakeProperties = PropertyUtils.getFakeProperties();

        // Building response
        ArrayList<HashMap> mapResponse = new ArrayList<>();
        for (Property property : fakeProperties) {

            PropertyListDTO propertyListDTO = PropertyListDTO.fromModel(property);

            HashMap mappedObject = new ObjectMapper().convertValue(propertyListDTO, HashMap.class);
            mappedObject.put("uri", "http://localhost/api/v1/property/".concat(property.getId().toString()));

            mapResponse.add(mappedObject);
        }
        String response = new ObjectMapper().writeValueAsString(mapResponse);

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response)).andReturn();
    }
}