package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.NullIdException;
import br.com.mercadolivre.desafioquality.exceptions.PropertyNotFoundException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.repository.ApplicationRepository;

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
    //

    public BigDecimal calcPropertyPrice(UUID propertyId) throws NullIdException, DatabaseReadException, DatabaseManagementException {
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

        return requestedPropertyNeighborhood.getValueDistrictM2().multiply(BigDecimal.valueOf(propertyArea));
    }

    public Double calcPropertyArea(Property property){
        if(property == null) {
            return 0.0;
        }

        RoomService roomService = new RoomService();

        return roomService.calcArea(property.getPropRooms());
    }
}
