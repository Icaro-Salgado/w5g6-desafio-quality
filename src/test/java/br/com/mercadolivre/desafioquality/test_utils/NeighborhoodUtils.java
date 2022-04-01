package br.com.mercadolivre.desafioquality.utils;

import br.com.mercadolivre.desafioquality.models.Neighborhood;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NeighborhoodUtils {

    public static List<Neighborhood> getMockList(Integer qty){
        List<Neighborhood> result = new ArrayList<>();
        for (int i = 0; i < qty; i++){
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
