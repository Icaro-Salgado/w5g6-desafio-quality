package br.com.mercadolivre.defafioquality.dto.mapper;

import br.com.mercadolivre.defafioquality.dto.request.PropertyDTO;
import br.com.mercadolivre.defafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.defafioquality.models.Property;


public class PropertyMapper {

    public static Property toProperty(PropertyDTO request) {

        Property property = new Property();
        property.setPropName(request.getPropName());
        property.setPropDistrict(request.getPropDistrict());
        property.setPropRooms(request.getPropRooms());

        return property;
    }

    public static PropertyValueDTO toPropertyResponse(Property property) {

        PropertyValueDTO response = new PropertyValueDTO();

        response.setPropName(property.getPropName());
        response.setPropDistrict(property.getPropDistrict());
        response.setPropValue(property.getPropValue());

        return response;
    }
}
