package br.com.mercadolivre.defafioquality.services;

import br.com.mercadolivre.defafioquality.models.Property;
import br.com.mercadolivre.defafioquality.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final ApplicationRepository<Property, UUID> propertyRepository;

    public void calcPropertyPrice(UUID propertyId){


    }
}
