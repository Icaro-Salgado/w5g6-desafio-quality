package br.com.mercadolivre.desafioquality.integration;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.utils.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class NeighborhoodIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseUtils<Neighborhood[]> databaseUtils;

    private final String filename = "neighborhood.json";

    @BeforeAll
    public void beforeAll() {
        databaseUtils.startDatabase(filename);
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        databaseUtils.loadDefaultFiles(filename, Neighborhood[].class);
    }

    @AfterAll
    public void afterAll(){
        databaseUtils.deleteDatabase();
    }

    @Test
    @DisplayName("NeighborhoodController - GET - /api/v1/neighborhood/{neighborhoodId}")
    public void testFindANeighborHoodById() throws Exception {
        List<Neighborhood> neighborhoods = new ArrayList<>();
        Neighborhood neighborhood = new Neighborhood(UUID.fromString("77b3737d-7450-4d94-8f95-936e2c17e2cc"), "São Paulo", BigDecimal.valueOf(2000.0));
        neighborhoods.add(neighborhood);

        databaseUtils.writeIntoFile(filename, neighborhoods);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/api/v1/neighborhood/{neighborhoodId}", "77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameDistrict").value("São Paulo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueDistrictM2").value(2000.0));
    }

    @Test
    @DisplayName("NeighborhoodController - DELETE - /api/v1/neighborhood/{neighborhoodId}")
    public void testDeleteANeighborHoodById() throws Exception {
        List<Neighborhood> neighborhoods = new ArrayList<>();
        Neighborhood neighborhood = new Neighborhood(UUID.fromString("77b3737d-7450-4d94-8f95-936e2c17e2cc"), "São Paulo", BigDecimal.valueOf(2000.0));
        neighborhoods.add(neighborhood);

        databaseUtils.writeIntoFile(filename, neighborhoods);

        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/api/v1/neighborhood/{neighborhoodId}", "77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());


        Neighborhood[] updatedNeighborhoods = databaseUtils.readFromFile(filename, Neighborhood[].class);
        Assertions.assertEquals(0, updatedNeighborhoods.length);
    }


    @Test
    @DisplayName("PropertyController - GET - /api/v1/neighborhood/")
    public void testNeighborhoodList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/neighborhood/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neighborhoods").isNotEmpty());
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/neighborhood/?size=1")
    public void testNeighborhoodListWithMultiplePages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/neighborhood/?size=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neighborhoods.length()").value(1));
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/neighborhood/?page=2&size=4")
    public void testNeighborhoodListInAnotherPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/neighborhood/?page=2&size=4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.neighborhoods.length()").value(1));
    }

    @Test
    @DisplayName("PropertyController - GET - /api/v1/neighborhood/?page=2&size=null")
    public void testNeighborhoodListErrorWhenReceiveInvalidParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/neighborhood/?page=2&size=null"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("NeighborhoodController - POST - /api/v1/neighborhood/")
    public void testPostNeighborhood() throws Exception {

        Neighborhood neighborhood = Neighborhood.builder()
                .nameDistrict("Vila Olímpia")
                .valueDistrictM2(BigDecimal.valueOf(45000))
                .build();

        ObjectMapper Obj = new ObjectMapper();

        String payload = Obj.writeValueAsString(neighborhood);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/neighborhood/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }
}
