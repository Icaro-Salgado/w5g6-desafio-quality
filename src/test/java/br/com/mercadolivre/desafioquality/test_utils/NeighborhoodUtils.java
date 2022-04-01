package br.com.mercadolivre.desafioquality.test_utils;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class NeighborhoodUtils {

    public static List<Neighborhood> getMockList(Integer qty) {
        List<Neighborhood> result = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            result.add(
                    new Neighborhood(
                            UUID.randomUUID(),
                            "District".concat(String.valueOf(i)),
                            new BigDecimal(i)
                    )
            );
        }

        return result;
    }

    public static void executePostTestExpectingBadRequest(
            Neighborhood neighborhoodToTest,
            String message,
            MockMvc mockMvc
    ) throws Exception {

        ObjectMapper Obj = new ObjectMapper();

        String payload = Obj.writeValueAsString(neighborhoodToTest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/neighborhood/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Optional<MethodArgumentNotValidException> someException = Optional.ofNullable((MethodArgumentNotValidException) result.getResolvedException());
        if (someException.isPresent()) {
            String msg = someException.get().getBindingResult().getAllErrors().get(0).getDefaultMessage();
            Assertions.assertEquals(msg, message);
        }
    }
}
