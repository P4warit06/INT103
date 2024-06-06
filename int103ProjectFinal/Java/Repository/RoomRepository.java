package Repository;

import domain.Room;

import java.util.stream.Stream;

public interface RoomRepository {
    Room createRoom(String roomNumber , String type,String capacity, String amenities , double price);
    Room retrieveRoom(String var1);
    boolean updateRoom(Room room);
    boolean deleteRoom(Room room);
    Stream<Room> stream();
}
