package br.com.mercadolivre.desafioquality.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Room {

    private UUID id;
    private String roomName;
    private Double roomWidth;
    private Double roomLength;
    private Double roomTotalArea;

    public Room(String roomName, Double roomWidth, Double roomLength) {
        this.roomName = roomName;
        this.roomWidth = roomWidth;
        this.roomLength = roomLength;
    }
}
