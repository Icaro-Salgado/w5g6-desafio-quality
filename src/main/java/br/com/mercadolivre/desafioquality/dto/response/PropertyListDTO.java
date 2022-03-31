package br.com.mercadolivre.desafioquality.dto.response;

import br.com.mercadolivre.desafioquality.models.Property;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
* Para ser utilizado quando você tem uma lista de Property
*/
@Data
@Builder
public class PropertyListDTO {
    UUID id;
    String propName, propDistrict;
    BigDecimal price;
    String uri;
    Integer roomsQty;


    public static List<PropertyListDTO> ToDtoList(List<Property> properties, UriComponentsBuilder uriBuilder, String basePath) {

        return properties.stream().map(property -> PropertyListDTO
                .builder()
                .id(property.getId())
                .propName(property.getPropName())
                .propDistrict(property.getPropDistrict())
                .price(property.getPropValue())
                .uri(uriBuilder.path(basePath.concat("/{id}")).buildAndExpand(property.getId()).toUri().toString())
                .roomsQty(property.getPropRooms().size())
                .build())
                .collect(Collectors.toList());
    }
}
