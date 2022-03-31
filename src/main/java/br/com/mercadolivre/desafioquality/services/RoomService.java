package br.com.mercadolivre.desafioquality.services;

import br.com.mercadolivre.desafioquality.models.Room;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RoomService {

    public Double calcArea(Room room) {
        if(room == null || room.getRoomWidth() == null || room.getRoomLength() == null){
            return 0.0;
        }

        if(room.getRoomWidth() < 0 || room.getRoomLength() < 0){
            return 0.0;
        }

        room.setRoomTotalArea(room.getRoomWidth() * room.getRoomLength());

        return room.getRoomTotalArea();
    }

    public Double calcArea(List<Room> rooms) {
        AtomicReference<Double> totalArea = new AtomicReference<>(0.0);
        for (Room room: rooms) {
            room.setRoomTotalArea(calcArea(room));
            if (room.getRoomTotalArea() == 0.0) {
                totalArea.set(0.0);
                break;
            }
            totalArea.updateAndGet(v -> v + room.getRoomTotalArea());
        }

        return totalArea.get();
    }

    public Room getBiggestRoom(List<Room> rooms) {
        double biggestRoom = 0d;
        Room retriveRoom = new Room();
        calcArea(rooms);
        for (Room r:rooms) {
            if (r.getRoomTotalArea() > biggestRoom) {
                biggestRoom = r.getRoomTotalArea();
                retriveRoom = r;
            }
        }
        return retriveRoom;
    }
}