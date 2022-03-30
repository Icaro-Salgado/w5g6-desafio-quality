package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.models.Room;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RoomService {

    public Double calcArea(Room room) {
        if(room == null){
            return 0.0;
        }

        room.setRoomTotalArea(room.getRoomWidth() * room.getRoomLength());

        return room.getRoomTotalArea();
    }

    public Double calcArea(List<Room> rooms) {
        AtomicReference<Double> totalArea = new AtomicReference<>(0.0);
        rooms.forEach(r -> {
            r.setRoomTotalArea(calcArea(r));
            totalArea.updateAndGet(v -> v + r.getRoomTotalArea());
        });

        return totalArea.get();
    }
}