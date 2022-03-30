package br.com.mercadolivre.desafioquality.service;

import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.services.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    private Double expected;
    final private RoomService roomService = new RoomService();

    @Test
    public void testIfTotalAreaPerRoomIsCorrect() {

        expected = 3.5 * 4.5;

        Room quartoEva = new Room("Quarto do Eva", 3.50, 4.5);
        Double areaQuartoEva = roomService.calcArea(quartoEva);

        assertEquals(expected, areaQuartoEva);
    }

    @Test
    public void testIfTotalAreaAnyRoomHasNegativeValue() {
        expected = 0.0;

        Room anyRoom = new Room("Quarto do Gasparzinho", -10.0, 8.40);
        Double areaTotal = roomService.calcArea(anyRoom);

        assertEquals(expected, areaTotal);
    }

    @Test
    public void testIfTotalAreaAnyRoomHasNullValue() {
        expected = 0.0;

        Room anyRoom = new Room("Quarto do Gasparzinho", 6.20, null);
        Double areaTotal = roomService.calcArea(anyRoom);

        assertEquals(expected, areaTotal);
    }

    @Test
    public void testIfTotalAreaAnyRoomIsNull() {
        expected = 0.0;

        Room nonExistingRoom = null;
        Double areaTotal = roomService.calcArea(nonExistingRoom);

        assertEquals(expected, areaTotal);
    }

    @Test
    public void testIfTotalAreaAllRoomsIsCorret() {

        expected = ((12.0 * 10.0) + (6.50 * 8.40) + (3.50*4.50));

        List<Room> rooms = List.of(
                new Room("Quarto Pedro", 12.0, 10.0),
                new Room("Quarto Klin", 6.50, 8.40),
                new Room("Quarto do Eva", 3.50, 4.5));

        Double areaTotal = roomService.calcArea(rooms);

        assertEquals(expected, areaTotal);
    }

    @Test
    public void testIfTotalAreaAnyRoomHasZeroAsWidth() {
        expected = 0.0;

        List<Room> rooms = List.of(
                new Room("Quarto Pedro", 12.0, 10.0),
                new Room("Quarto Klin", 0.0, 8.40),
                new Room("Quarto do Eva", 3.50, 4.5));

        Double areaTotal = roomService.calcArea(rooms);

        assertEquals(expected, areaTotal);
    }

    @Test
    public void testIfReturnErrorWhenReceiveNullValue(){
        expected = 0.0;
        Double roomArea = roomService.calcArea((Room) null);

        assertEquals(expected, roomArea);

    }

}
