package repository;

import domain.Room;

import java.util.stream.Stream;

public interface RoomRepository {
    Room createRoom(String type,String capacity, String amenities , double price);
    Room retrieveRoom(String roomNumber);
    boolean updateRoom(Room room);
    boolean deleteRoom(Room room);
    Stream<Room> stream();
}
