package Repository.memory;

import Repository.RoomRepository;
import domain.Room;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryRoomRepository implements RoomRepository {
    private long NextRoomId = 1 ;
    private final Map<String, Room> repo;
    public InMemoryRoomRepository() {repo = new TreeMap<>();}

    @Override
    public Room createRoom(String roomNumber , String type,String capacity, String amenities , double price ) {
        if ( roomNumber == null || roomNumber.isBlank() || type == null || type.isBlank() || capacity == null
         || capacity.isBlank() || amenities == null || amenities.isBlank() || price < 0.0) return null;
        String id = String.format("C%010d", NextRoomId);
        if (repo.containsKey(id)) return null ;
        Room room = new Room (id,type,capacity,amenities,price);
        repo.put(id ,room) ;
        ++NextRoomId;
        return room ;
    }

    @Override
    public Room retrieveRoom(String roomNumber) {
        if (roomNumber == null || roomNumber.isBlank()) return null;
        return repo.get(roomNumber);
    }

    @Override
    public boolean updateRoom(Room room) {
        if (room == null)  return false ;
        repo.replace(room.getRoomNumber(), room);
        return true;
    }

    @Override
    public boolean deleteRoom(Room room) {
        if (room == null) return false;
        repo.remove(room.getRoomNumber(), room);
        return true;

    }
    @Override
    public Stream<Room> stream() {
        return repo.values().stream();
    }
}