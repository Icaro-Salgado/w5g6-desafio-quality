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
}
