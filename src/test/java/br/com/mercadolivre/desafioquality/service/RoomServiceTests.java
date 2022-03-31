package br.com.mercadolivre.desafioquality.service;

import br.com.mercadolivre.desafioquality.models.Room;
import br.com.mercadolivre.desafioquality.services.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    private Double expected;
    final private RoomService roomService = new RoomService();

    @Test
    @DisplayName("Given a valid room, when call calcArea, then return width multiplied by length")
    public void testIfTotalAreaPerRoomIsCorrect() {
        expected = 3.5 * 4.5;
        Room quartoEva = new Room("Quarto do Eva", 3.50, 4.5);

        Double areaQuartoEva = roomService.calcArea(quartoEva);

        assertEquals(expected, areaQuartoEva);
    }

    @Test
    @DisplayName("Given a room containing negative width, when call calcArea, then return 0.0")
    public void testIfTotalAreaAnyRoomHasNegativeValue() {
        expected = 0.0;
        Room anyRoom = new Room("Quarto do Gasparzinho", -10.0, 8.40);

        Double areaTotal = roomService.calcArea(anyRoom);

        assertEquals(expected, areaTotal);
    }

    @Test
    @DisplayName("Given a room containing null length, when call calcArea, then return 0.0")
    public void testIfTotalAreaAnyRoomHasNullValue() {
        expected = 0.0;
        Room anyRoom = new Room("Quarto do Gasparzinho", 6.20, null);

        Double areaTotal = roomService.calcArea(anyRoom);

        assertEquals(expected, areaTotal);
    }

    @Test
    @DisplayName("Given a non existing room, when call calcArea, then return 0.0")
    public void testIfTotalAreaAnyRoomIsNull() {
        expected = 0.0;
        Room nonExistingRoom = null;

        Double areaTotal = roomService.calcArea(nonExistingRoom);

        assertEquals(expected, areaTotal);
    }

    @Test
    @DisplayName("Given a list of valid rooms, when call calcArea, then return the sum of all room area")
    public void testIfTotalAreaAllRoomsIsCorret() {
        expected = ((12.0 * 10.0) + (6.50 * 8.40) + (3.50 * 4.50));
        List<Room> rooms = List.of(
                new Room("Quarto Pedro", 12.0, 10.0),
                new Room("Quarto Klin", 6.50, 8.40),
                new Room("Quarto do Eva", 3.50, 4.5));

        Double areaTotal = roomService.calcArea(rooms);

        assertEquals(expected, areaTotal);
    }

    @Test
    @DisplayName("Given a list of rooms where one of them contains 0.0 width, when call calcArea, then return 0.0")
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
    public void testIfBiggestRoomIsCorrect() {
        Room bedroom = new Room("quarto", 12.0, 10.0);
        Room livingRoom = new Room("sala", 2.0, 8.40);
        Room kitchen = new Room("cozinha", 3.50, 4.5);
        List<Room> rooms = List.of(bedroom, livingRoom, kitchen);

        Room biggestRoom = roomService.getBiggestRoom(rooms);

        assertEquals(bedroom, biggestRoom);
    }

}
