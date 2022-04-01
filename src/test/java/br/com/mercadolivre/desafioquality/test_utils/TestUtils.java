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

import java.util.Optional;

public class TestUtils {

    public static void assertErrorMessage(
            Object object,
            String endpoint,
            String message,
            MockMvc mockMvc
    ) throws Exception {

        ObjectMapper Obj = new ObjectMapper();

        String payload = Obj.writeValueAsString(object);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
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
