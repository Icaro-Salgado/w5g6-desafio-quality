package br.com.mercadolivre.desafioquality.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
