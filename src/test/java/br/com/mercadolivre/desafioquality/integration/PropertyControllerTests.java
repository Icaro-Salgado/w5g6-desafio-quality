package br.com.mercadolivre.desafioquality.integration;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.test_utils.DatabaseUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private List<Property> populateFakeDatabase() throws IOException {
        List<Property> properties = new ArrayList<>();

        Neighborhood fakeNeighborhood = Neighborhood
                .builder()
                .nameDistrict("Bairro legal")
                .valueDistrictM2(BigDecimal.valueOf(200.0))
                .build();

        databaseUtils.writeIntoFile(neighborhoodsFile, List.of(fakeNeighborhood));

        List<Room> rooms = List.of(
                Room.builder().roomName("Ola").roomWidth(2.0).roomLength(34.0).build()
        );

        Property property = Property
                .builder()
                .id(UUID.fromString("77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                .propName("Casa sem nome")
                .propDistrict("Bairro legal")
                .propRooms(rooms)
                .build();
        properties.add(property);

        databaseUtils.writeIntoFile(propertyFile, properties);

        return properties;
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/property-area/{propertyId}")
    public void testCalculatePropertyArea() throws Exception {
        populateFakeDatabase();

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/property-area/{propertyId}", "77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalArea").value(68.0));
    }
}