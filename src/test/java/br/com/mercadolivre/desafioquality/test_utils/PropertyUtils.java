package br.com.mercadolivre.desafioquality.test_utils;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PropertyUtils {

    public static Property buildMockProperty(){
        List<Room> rooms = List.of(
                new Room("Bedroom", 12.0, 10.0),
                new Room("Dinner room", 6.50, 8.40),
                new Room("Bathroom", 3.50, 4.5));

        Neighborhood fakeNeighborhood = new Neighborhood();
        fakeNeighborhood.setValueDistrictM2(BigDecimal.valueOf(100));

        String fakeNeighborhoodName = "Fake neighborhood";
        fakeNeighborhood.setNameDistrict(fakeNeighborhoodName);

        Property fakeProperty = new Property();
        fakeProperty.setPropRooms(rooms);
        fakeProperty.setPropDistrict(fakeNeighborhoodName);

        return fakeProperty;
    }

    private HashMap<String, List> populateFakeDatabase() throws IOException {

        List<Property> properties = new ArrayList<>();
        List<Neighborhood> neighborhoods = new ArrayList<>();

        // Neighborhood
        Neighborhood fakeNeighborhood = Neighborhood
                .builder()
                .nameDistrict("Bairro legal")
                .valueDistrictM2(BigDecimal.valueOf(200.0))
                .build();

        neighborhoods.add(fakeNeighborhood);

        // Property
        List<Room> rooms = List.of(
                Room.builder().roomName("Ola").roomWidth(2.0).roomLength(34.0).build()
        );

        Property property = Property
                .builder()
                .id(UUID.fromString("77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                .propName("Casa sem nome")
                .propDistrict("Bairro legal")
                .propRooms(rooms)
                .propArea(68.0)
                .propValue(BigDecimal.valueOf(200.0).multiply(BigDecimal.valueOf(68)))
                .build();
        properties.add(property);

        rooms = List.of(
                Room.builder().roomName("Ola").roomWidth(2.0).roomLength(34.0).build(),
                Room.builder().roomName("Ola 2").roomWidth(2.0).roomLength(34.0).build()
        );

        property = Property
                .builder()
                .id(UUID.fromString("992b27ac-3f12-42e2-9925-b2f922c48ded"))
                .propName("Casa com nome")
                .propDistrict("Bairro legal")
                .propArea(68.0 * 2)
                .propValue(BigDecimal.valueOf(200.0).multiply(BigDecimal.valueOf(68 * 2)))
                .propRooms(rooms)
                .build();
        properties.add(property);

        HashMap<String, List> toMock = new HashMap<>();

        toMock.put("properties", properties);
        toMock.put("neighborhoods", neighborhoods);

        return toMock;
    }

    public static void getMockDatabase() {

    }
}
