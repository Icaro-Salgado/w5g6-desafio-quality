package br.com.mercadolivre.defafioquality.services;

import br.com.mercadolivre.defafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.defafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.defafioquality.exceptions.NullIdException;
import br.com.mercadolivre.defafioquality.exceptions.PropertyNotFoundException;
import br.com.mercadolivre.defafioquality.models.Neighborhood;
import br.com.mercadolivre.defafioquality.models.Property;
import br.com.mercadolivre.defafioquality.repository.ApplicationRepository;
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

        // Como o método que calcula a área da propriedade não esta pronto vou
        // colocar um hard input qualquer para a área da propriedade
        Double propertyArea = 32.0;

        // TODO: Verificar se é a melhor maneira... após obter o calculo do preço já setar na propia propiedade
        BigDecimal propertyFinalPrice = requestedPropertyNeighborhood.getValueDistrictM2().multiply(BigDecimal.valueOf(propertyArea));

        //seta o valor da propiedade requisitada no seter de valor
        requestedProperty.setPropValue(propertyFinalPrice);

        return requestedProperty;
    }

    public Property findByID(UUID propertyId) {
        return null;
    }
}
