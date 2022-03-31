package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
public class NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;

    public void createNeighborhood(){}

    // offset and limit
    public List<Neighborhood> listNeighborhood(Integer page, Integer limit) throws DatabaseReadException {
        Integer offSet = page == null || page <= 1 ? 0 : (page - 1)  * limit;

        return neighborhoodRepository.read(offSet, limit);
    }

    public void getNeighborhoodById(){}

    public void deleteNeighborhoodById(){}

    public Integer getTotalPages(Integer limit) throws DatabaseReadException {
        int results = neighborhoodRepository.read().size();

        return new BigDecimal(results).divide(new BigDecimal(limit), RoundingMode.CEILING).intValue();
    }
}
