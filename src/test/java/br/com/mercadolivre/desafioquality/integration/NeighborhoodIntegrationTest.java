package br.com.mercadolivre.desafioquality.integration;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.utils.DatabaseUtils;
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
public class NeighborhoodIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatabaseUtils<Neighborhood[]> databaseUtils;

    private String filename = "neighborhood.json";

    @BeforeAll
    public void beforeAll() {
        databaseUtils.startDatabase(filename);
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        databaseUtils.resetDatabase(filename);
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

}
