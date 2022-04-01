package br.com.mercadolivre.desafioquality.test_utils;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PropertyUtils {

    private static List<Neighborhood> neighborhoods = new ArrayList<>();
    private static List<Property> properties = new ArrayList<>();

    static {
        neighborhoods.add(
                Neighborhood.builder().nameDistrict("Bairro legal").valueDistrictM2(BigDecimal.valueOf(200.0)).build()
        );

        properties.addAll(
                List.of(
                        Property
                                .builder()
                                .id(UUID.fromString("77b3737d-7450-4d94-8f95-936e2c17e2cc"))
                                .propName("Casa sem nome")
                                .propDistrict(neighborhoods.get(0).getNameDistrict())
                                .propRooms(List.of(Room.builder().roomName("Ola").roomWidth(2.0).roomLength(4.0).build()))
                                .propArea(8.0)
                                .propValue(BigDecimal.valueOf(8).multiply(neighborhoods.get(0).getValueDistrictM2()))
                                .build(),
                        Property
                                .builder()
                                .id(UUID.fromString("992b27ac-3f12-42e2-9925-b2f922c48ded"))
                                .propName("Casa com nome")
                                .propDistrict(neighborhoods.get(0).getNameDistrict())
                                .propRooms(List.of(Room.builder().roomName("Ola").roomWidth(2.0).roomLength(4.0).build()))
                                .propArea(8.0)
                                .propValue(BigDecimal.valueOf(8).multiply(neighborhoods.get(0).getValueDistrictM2()))
                                .build()
                )
        );
    }

    public static Property buildMockProperty() {
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

    public static List<Neighborhood> getFakeNeighborhoods() {
        return neighborhoods;
    }

    public static List<Property> getFakeProperties() {
        return properties;
    }
}
