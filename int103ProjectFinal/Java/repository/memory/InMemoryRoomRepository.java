package repository.memory;

import repository.RoomRepository;
import domain.Room;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryRoomRepository implements RoomRepository {
    private long NextRoomId = 1 ;
    private final Map<String, Room> repository ;
    public InMemoryRoomRepository() {
        repository = new TreeMap<>();
    }

    @Override
    public Room createRoom(String roomNumber , String type,String capacity, String amenities , double price ) {
        if ( roomNumber == null || roomNumber.isBlank() || type == null || type.isBlank() || capacity == null
         || capacity.isBlank() || amenities == null || amenities.isBlank() || price < 0.0) {
            return null;
        }
        String id = String.format("C%010d", NextRoomId);
        if (repository.containsKey(id))
            return null ;
        Room room =  new Room(id ,type,capacity,amenities,price);
        repository.put(id ,room) ;
        ++NextRoomId;
        return room ;
    }

    @Override
    public Room retrieveRoom(String roomNumber) {
        if (roomNumber == null || roomNumber.isBlank()) {
            return null;
        }
        return repository.get(roomNumber);
    }

    @Override
    public boolean updateRoom(Room room) {
        if (room == null)  return false ;
        repository.replace(room.getRoomNumber(), room);
        return false;
    }

    @Override
    public boolean deleteRoom(Room room) {
        if (room == null) return false;
        return  repository.remove(room.getRoomNumber(), room);

    }
    @Override
    public Stream<Room> stream() {
        return repository.values().stream();
    }
}