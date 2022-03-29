package br.com.mercadolivre.defafioquality.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Room {

    private UUID id;
    private String roomName;
    private Double roomWidth;
    private Double roomLength;

}
