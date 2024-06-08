package repository;

import domain.Room;

import java.util.Map;
import java.util.stream.Stream;

public interface RoomRepository {
    Room createRoom(String type,String capacity, String amenities , double price);
    Room retrieveRoom(String roomNumber);
    Map<String, Room> getAllRoom();
    boolean updateRoom(Room room);
    boolean deleteRoom(Room room);
    Stream<Room> stream();
}
