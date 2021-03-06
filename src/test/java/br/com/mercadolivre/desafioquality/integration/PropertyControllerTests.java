package br.com.mercadolivre.desafioquality.integration;

import br.com.mercadolivre.desafioquality.dto.request.CreatePropertyDTO;
import br.com.mercadolivre.desafioquality.dto.request.CreateRoomsDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyDetailDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyListDTO;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.test_utils.DatabaseUtils;
import br.com.mercadolivre.desafioquality.test_utils.PropertyUtils;
import br.com.mercadolivre.desafioquality.dto.request.UpdatePropertyDTO;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.test_utils.DatabaseUtils;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

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
        List<Property> properties = new ArrayList<>();

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

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/{propertyId}")
    public void testDetailProperty() throws Exception {
        // SETUP
        this.populateFakeDatabase();
        Property fakeProperty = PropertyUtils.getFakeProperties().get(0);

        // Building response
        PropertyDetailDTO propertyDetailDTO = PropertyDetailDTO.fromModel(fakeProperty);
        HashMap mappedObject = new ObjectMapper().convertValue(propertyDetailDTO, HashMap.class);
        String response = new ObjectMapper().writeValueAsString(mappedObject);

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/{propertyId}", fakeProperty.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response)).andReturn();
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/property-value/{propertyId}")
    public void testCalculatedPropertyValue() throws Exception {
        // SETUP
        this.populateFakeDatabase();
        Property fakeProperty = PropertyUtils.getFakeProperties().get(0);

        // Building response
        BigDecimal response = fakeProperty.getPropValue();

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/property-value/{propertyId}", fakeProperty.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .equals(response);
    }

    @Test
    @DisplayName("PropertyController - POST - /api/v1/property")
    public void testPostProperty() throws Exception {
        // SETUP
        this.populateFakeDatabase();
        Property fakeProperty = PropertyUtils.getFakeProperties().get(0);

        // Building payload
        List<Room> propRooms = fakeProperty.getPropRooms();

        List<CreateRoomsDTO> roomDTOS = new ArrayList<>();
        for (Room room : propRooms) {
            roomDTOS.add(
                    CreateRoomsDTO
                            .builder()
                            .name(room.getRoomName())
                            .length(room.getRoomLength())
                            .width(room.getRoomWidth())
                            .build()
            );
        }

        CreatePropertyDTO createPropertyDTO = CreatePropertyDTO
                .builder()
                .propName(fakeProperty.getPropName())
                .propDistrict(fakeProperty.getPropDistrict())
                .rooms(roomDTOS)
                .build();

        HashMap mappedPayload = new ObjectMapper().convertValue(createPropertyDTO, HashMap.class);
        String payload = new ObjectMapper().writeValueAsString(mappedPayload);

        mockMvc.perform(MockMvcRequestBuilders
        .post("/api/v1/property")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("PropertyController - DELETE - /api/v1/property/{propertyId}")
    public void testDeleteProperty() throws Exception {
        populateFakeDatabase();
        UUID id = UUID.fromString("77b3737d-7450-4d94-8f95-936e2c17e2cc");

        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/api/v1/property/{propertyId}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        AtomicBoolean deleted = new AtomicBoolean(true);

        Property[] properties = databaseUtils.readFromFile(propertyFile, Property[].class);
        Arrays.stream(properties).forEach(property -> {
            if (property.getId().equals(id)) {
                deleted.set(false);
            }
        });

        Assertions.assertTrue(deleted.get());
    }
}