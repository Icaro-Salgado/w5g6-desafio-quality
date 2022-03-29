package br.com.mercadolivre.defafioquality.models;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Property {

    private UUID id;
    private String propName;
    private String propDistrict;
    private List<Room> propRooms;

}
