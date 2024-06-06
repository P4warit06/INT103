package Repository;

import domain.Room;

import java.util.stream.Stream;

public interface RoomRepository {
    Room Retrieve(String var1);
    boolean Update(Room var1);
    boolean Delete(Room var1);
    Stream<Room> stream();
}
