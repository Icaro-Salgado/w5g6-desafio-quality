package br.com.mercadolivre.desafioquality.test_utils;

import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.models.Property;
import br.com.mercadolivre.desafioquality.models.Room;

import java.math.BigDecimal;
import java.util.List;

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
}
