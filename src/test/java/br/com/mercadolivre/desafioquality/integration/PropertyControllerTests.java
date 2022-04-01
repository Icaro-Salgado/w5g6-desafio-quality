package br.com.mercadolivre.desafioquality.integration;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.test_utils.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

    // TODO: Extrair método para a propety test utils, mas depois que fizer todos os testes de integração
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
                .propArea(68.0)
                .propValue(BigDecimal.valueOf(200.0).multiply(BigDecimal.valueOf(68)))
                .build();
        properties.add(property);

        rooms = List.of(
                Room.builder().roomName("Ola").roomWidth(2.0).roomLength(34.0).build(),
                Room.builder().roomName("Ola 2").roomWidth(2.0).roomLength(34.0).build()
        );

        property = Property
                .builder()
                .id(UUID.fromString("992b27ac-3f12-42e2-9925-b2f922c48ded"))
                .propName("Casa com nome")
                .propDistrict("Bairro legal")
                .propArea(68.0 * 2)
                .propValue(BigDecimal.valueOf(200.0).multiply(BigDecimal.valueOf(68 * 2)))
                .propRooms(rooms)
                .build();
        properties.add(property);

        databaseUtils.writeIntoFile(propertyFile, properties);

        return properties;
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/property-area/{propertyId}")
    public void testCalculatePropertyArea() throws Exception {
        Property fakeProperty = this.populateFakeDatabase().get(0);

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/property-area/{propertyId}", fakeProperty.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalArea").value(68.0));
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/property/")
    public void testListProperties() throws Exception {
        List<Property> fakeProperties = populateFakeDatabase();

        // Building response
        ArrayList<HashMap> mapResponse = new ArrayList<>();
        for (Property property : fakeProperties) {
            HashMap mappedObject = new ObjectMapper().convertValue(property, HashMap.class);

            // Removing what is not used on list view
            mappedObject.remove("largestRoomId");
            mappedObject.remove("propRooms");
            mappedObject.remove("propArea");

            // Changing some key values
            Object obj = mappedObject.remove("propValue");
            mappedObject.put("price", obj);

            // Adding extra returns
            mappedObject.put("uri", "http://localhost/api/v1/property/".concat(property.getId().toString()));
            mappedObject.put("roomsQty", property.getPropRooms().size());

            mapResponse.add(mappedObject);
        }
        String response = new ObjectMapper().writeValueAsString(mapResponse);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/property/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response)).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}