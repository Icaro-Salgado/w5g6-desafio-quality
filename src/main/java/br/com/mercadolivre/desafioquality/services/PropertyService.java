
package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.exceptions.*;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.repository.ApplicationRepository;

import br.com.mercadolivre.desafioquality.exceptions.NeighborhoodNotFoundException;
import br.com.mercadolivre.desafioquality.services.validators.NeighborhoodValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final ApplicationRepository<Property, UUID> propertyRepository;

    // São declarados no contexto de classe, pois assim quem se encarrega de instânciar é o Spring
    private final ApplicationRepository<Neighborhood, UUID> neighborhoodRepository;
    private final NeighborhoodValidationService neighborhoodValidationService;
    //

    public Property addProperty(Property propertyToAdd)
            throws DbEntryAlreadyExists, DatabaseManagementException, DatabaseWriteException, DatabaseReadException, NeighborhoodNotFoundException {

        // Validations, por enquanto só temos a validação caso o bairro exista
        neighborhoodValidationService.validate(propertyToAdd.getPropDistrict());

        // Entity builder, ele seta coisas que serão persistidas
        this.makePropertyEntity(propertyToAdd);

        // Se ele não conseguir adicionar a property ele vai jogar um erro para ser tratado no Advice do controller
        return propertyRepository.add(propertyToAdd);
    }

    private void makePropertyEntity(Property property) throws DatabaseReadException, DatabaseManagementException {
        property.setId(UUID.randomUUID());
        property.setPropArea(this.calcPropertyArea(property));
        property.setPropValue(this.calcPropertyPrice(property)); // TODO: Colocar o valor somente no read
    }

    private BigDecimal calcPropertyPrice(Property property) throws DatabaseReadException, DatabaseManagementException {
        List<Neighborhood> neighborhoodList = neighborhoodRepository.read();

        Optional<Neighborhood> propertyNeighborhood = neighborhoodList
                .stream()
                .filter(n -> n.getNameDistrict().equals(property.getPropDistrict()))
                .findFirst();

        if (propertyNeighborhood.isEmpty()) {
            throw new NeighborhoodNotFoundException("Bairro não encontrado");
        }
        Neighborhood neighborhood = propertyNeighborhood.get();

        return neighborhood.getValueDistrictM2().multiply(BigDecimal.valueOf(this.calcPropertyArea(property)));
    }

    public Property getPropertyPrice(UUID propertyId) throws NullIdException, DatabaseReadException, DatabaseManagementException {
        if(propertyId == null) {
            throw new NullIdException("O id é nulo!");
        }

        Optional<Property> response = propertyRepository.find(propertyId);

        if(response.isEmpty()) {
            throw new PropertyNotFoundException("Propriedade não encontrada");
        }

        Property requestedProperty = response.get();

        List<Neighborhood> neighborhoodList = neighborhoodRepository.read();

        Optional<Neighborhood> requestedPropertyNeighborhood = neighborhoodList
                .stream()
                .filter(n -> n.getNameDistrict().equals(requestedProperty.getPropDistrict()))
                .findFirst();

        Neighborhood neighborhood;
        if (requestedPropertyNeighborhood.isEmpty()) {
            throw new NeighborhoodNotFoundException("Bairro não encontrado");
        }

        requestedProperty.setPropValue(this.calcPropertyPrice(requestedProperty));

        return requestedProperty;
    }

    public Property findProperty(UUID propertyId) throws PropertyNotFoundException, DatabaseReadException, DatabaseManagementException {
        if(propertyId == null) {
            throw new NullIdException("O id é nulo!");
        }

        Optional<Property> response = propertyRepository.find(propertyId);

        if(response.isEmpty()) {
            throw new PropertyNotFoundException("Propriedade não encontrada");
        }

        return response.get();
    }

    public Double calcPropertyArea(Property property) throws PropertyNotFoundException {
        if(property == null) {
            throw new PropertyNotFoundException("Propriedade não encontrada");
        }

        RoomService roomService = new RoomService();

        return roomService.calcArea(property.getPropRooms());
    }


    public Property find(UUID id) throws DatabaseReadException, DatabaseManagementException {
        return propertyRepository.find(id).orElse(new Property());
    }

    public List<Property> ListProperties() throws DatabaseReadException, DatabaseManagementException {
        return propertyRepository.read();
    }
}
