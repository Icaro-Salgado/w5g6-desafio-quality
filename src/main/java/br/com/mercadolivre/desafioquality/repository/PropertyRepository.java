package br.com.mercadolivre.desafioquality.repository;

import br.com.mercadolivre.desafioquality.database.FileManager;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.exceptions.DbEntryAlreadyExists;
import br.com.mercadolivre.desafioquality.models.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PropertyRepository implements ApplicationRepository<Property, UUID> {

    private final FileManager<Property[]> fileManager;
    private final String filename = "property.json";

    @Override
    public List<Property> read() throws DatabaseReadException {
        Property[] properties = fileManager.readFromFile(filename, Property[].class);

        if (properties.length == 0) {
            return new ArrayList<>();
        }

        return Arrays.stream(properties).collect(Collectors.toList());
    }

    @Override
    public Optional<Property> find(UUID id) throws DatabaseReadException {
        Property[] properties = fileManager.readFromFile(filename, Property[].class);

        return Arrays.stream(properties).filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public Property add(Property propertyToAdd) throws DatabaseWriteException, DatabaseReadException, DbEntryAlreadyExists {
        List<Property> properties = read();

        if (properties.contains(propertyToAdd)) {
            throw new DbEntryAlreadyExists("Esta propriedade já esta cadastrada na base!");
        }

        propertyToAdd.setId(UUID.randomUUID());
        properties.add(propertyToAdd);
        fileManager.writeIntoFile(filename, properties);

        return propertyToAdd;
    }

    @Override
    public void update(UUID id, Property updatedProperty) throws DatabaseWriteException, DatabaseReadException {
        List<Property> properties = read();
        properties = properties.stream()
                .map(property -> property.getId().equals(id) ? updatedProperty : property)
                .collect(Collectors.toList());
        fileManager.writeIntoFile(filename, properties);
    }

    @Override
    public void delete(UUID propertyId) throws DatabaseReadException, DatabaseWriteException {
        List<Property> properties = read();

        properties = properties.stream()
                .filter(property -> !property.getId().equals(propertyId))
                .collect(Collectors.toList());
        fileManager.writeIntoFile(filename, properties);
    }
}
