
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
import java.util.ArrayList;
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

        neighborhoodValidationService.validate(propertyToAdd.getPropDistrict());

        propertyToAdd.setId(UUID.randomUUID());
        return propertyRepository.add(propertyToAdd);
    }

    public Property calcPropertyPrice(UUID propertyId) throws NullIdException, DatabaseReadException, DatabaseManagementException {
        if(propertyId == null) {
            throw new NullIdException("O id é nulo!");
        }

        Optional<Property> response = propertyRepository.find(propertyId);

        if(response.isEmpty()) {
            throw new PropertyNotFoundException("Propriedade não encontrada");
        }

        Property requestedProperty = response.get();

        List<Neighborhood> neighborhoodList = neighborhoodRepository.read();

        // TODO: Verificar com o pessoal de cadastro de propriedade se posso usar o get direto ou devo checar primeiro se achou
        Neighborhood requestedPropertyNeighborhood = neighborhoodList
                .stream()
                .filter(n -> n.getNameDistrict().equals(requestedProperty.getPropDistrict()))
                .findFirst().get();

        Double propertyArea = calcPropertyArea(requestedProperty);

        // TODO: Verificar se é a melhor maneira... após obter o calculo do preço já setar na propia propiedade
        BigDecimal propertyFinalPrice = requestedPropertyNeighborhood.getValueDistrictM2().multiply(BigDecimal.valueOf(propertyArea));

        //seta o valor da propiedade requisitada no seter de valor
        requestedProperty.setPropValue(propertyFinalPrice);

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
