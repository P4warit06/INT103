package Repository.memory;

import Repository.RoomRepository;
import domain.Room;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryRoomRepository implements RoomRepository {
    private final Map<String, Room> repository = new TreeMap();
    public InMemoryRoomRepository() {
    }
    public Room Retrieve(String roomNumber) {
        return (Room) this.repository.get(roomNumber);
    }
    public boolean Update(Room room) {
        if (room == null) {
            return false;
        } else {
            this.repository.replace(room.getRoomNumber(), room);
            return true;
        }
    }
    public boolean Delete(Room room) {
        return room == null ? false : this.repository.remove(room.getRoomNumber(), room);
    }
    public Stream<Room> stream() {
        return this.repository.values().stream();
    }
}