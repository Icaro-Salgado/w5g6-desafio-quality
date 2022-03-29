package br.com.mercadolivre.defafioquality.models;

import lombok.Data;

import java.util.List;

@Data
public class Property {

    private String propName;
    private String propDistrict;
    private List<Room> propRooms;

}
