package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.exceptions.DbEntryAlreadyExists;
import br.com.mercadolivre.desafioquality.exceptions.NeighborhoodNotFoundException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.repository.NeighborhoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NeighborhoodService {

    private NeighborhoodRepository neighborhoodRepository;

    public Neighborhood createNeighborhood(Neighborhood newNeighborhood) throws DbEntryAlreadyExists, DatabaseWriteException, DatabaseReadException {

        newNeighborhood.setId(UUID.randomUUID());
        return neighborhoodRepository.add(newNeighborhood);
    }

    public void listNeighborhood(){}

    public Neighborhood getNeighborhoodById(UUID id) throws DatabaseReadException {
        Optional<Neighborhood> neighborhoodExists = this.neighborhoodRepository.find(id);
        if (neighborhoodExists.isPresent()) {
            return neighborhoodExists.get();
        }
        throw new NeighborhoodNotFoundException("Bairro não encontrado");
    }

    public void deleteNeighborhoodById(UUID id) throws DatabaseReadException, DatabaseWriteException {
        Neighborhood neighborhood = getNeighborhoodById(id);
        this.neighborhoodRepository.delete(neighborhood);
    }
}
