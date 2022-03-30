package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.exceptions.NullIdException;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final ApplicationRepository<Property, UUID> propertyRepository;

    public BigDecimal calcPropertyPrice(UUID propertyId) throws NullIdException {
        return null;
    }
}
