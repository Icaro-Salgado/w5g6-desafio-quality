package br.com.mercadolivre.desafioquality.repository;

import br.com.mercadolivre.desafioquality.database.FileManager;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.exceptions.DbEntryAlreadyExists;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class NeighborhoodRepository implements ApplicationRepository<Neighborhood, UUID> {

    private final FileManager<Neighborhood[]> fileManager;
    private final String filename = "neighborhood.json";

    @Override
    public List<Neighborhood> read() throws DatabaseReadException {
        Neighborhood[] neighborhoods = fileManager.readFromFile(filename, Neighborhood[].class);

        if (neighborhoods.length == 0) {
            return new ArrayList<>();
        }

        return Arrays.stream(neighborhoods).collect(Collectors.toList());
    }

    @Override
    public Optional<Neighborhood> find(UUID id) throws DatabaseReadException {
        Neighborhood[] neighborhoods = fileManager.readFromFile(filename, Neighborhood[].class);

        return Arrays.stream(neighborhoods).filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public Neighborhood add(Neighborhood neighborhoodToAdd) throws DatabaseWriteException, DatabaseReadException, DbEntryAlreadyExists {
        List<Neighborhood> neighborhoods = read();

        if (neighborhoods.contains(neighborhoodToAdd)) {
            throw new DbEntryAlreadyExists(neighborhoodToAdd.getNameDistrict().concat(" já está cadastrado na base de dados"));
        }

        neighborhoodToAdd.setId(UUID.randomUUID());
        neighborhoods.add(neighborhoodToAdd);
        fileManager.writeIntoFile(filename, neighborhoods);

        return neighborhoodToAdd;
    }

    @Override
    public Integer update(Map<String, Object> filters, Map<String, Object> values) {
        return 0;
    }
}
