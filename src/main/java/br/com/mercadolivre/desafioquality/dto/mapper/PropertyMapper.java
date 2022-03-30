package br.com.mercadolivre.desafioquality.dto.mapper;

import br.com.mercadolivre.desafioquality.dto.request.PropertyDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyResponseDTO;
import br.com.mercadolivre.desafioquality.dto.response.PropertyValueDTO;
import br.com.mercadolivre.desafioquality.models.Property;

import java.util.List;
import java.util.stream.Collectors;


public class PropertyMapper {

    public static Property toProperty(PropertyDTO request) {

        Property property = new Property();
        property.setPropName(request.getPropName());
        property.setPropDistrict(request.getPropDistrict());
        property.setPropRooms(request.getPropRooms());

        return property;
    }

    public static PropertyResponseDTO toPropertyResponse(Property property) {

        PropertyResponseDTO response = new PropertyResponseDTO();

        response.setPropName(property.getPropName());
        response.setPropDistrict(property.getPropDistrict());
        response.setPropRooms(response.getPropRooms());
        response.setPropValue(property.getPropValue());

        return response;
    }

    public static List<PropertyResponseDTO> toPropertyResponse(List<Property> properties) {
        return properties.stream().map(PropertyMapper::toPropertyResponse).collect(Collectors.toList());
    }


    public static PropertyValueDTO toPropertyValueResponse(Property property) {

        PropertyValueDTO response = new PropertyValueDTO();

        response.setPropName(property.getPropName());
        response.setPropDistrict(property.getPropDistrict());
        response.setPropValue(property.getPropValue());

        return response;
    }

    public static PropertyValueDTO toPropertyResponseArea(Property property, Double totalArea) {

        PropertyValueDTO response = new PropertyValueDTO();

        response.setPropName(property.getPropName());
        response.setPropDistrict(property.getPropDistrict());
        response.setPropValue(property.getPropValue());
        response.setNumberOfRooms(property.getPropRooms().size());

        if (totalArea != null) {
            response.setTotalArea(totalArea);
        }

        return response;
    }

}
