package repository.file;

import repository.RoomRepository;
import domain.Person;
import domain.Room;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FileRoomRepository implements RoomRepository {
    private String filename = "room.dat";
    private long nextRoomNumber = 0;
    private Map<String, Room> repo;

    public FileRoomRepository() {
        File fileRoom = new File(filename);
        if (fileRoom.exists()) {
            try(FileInputStream fi = new FileInputStream(fileRoom);
                BufferedInputStream bi = new BufferedInputStream(fi);
                ObjectInputStream oi = new ObjectInputStream(bi);) {
                nextRoomNumber = oi.readLong();
                repo = (Map<String, Room>) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                nextRoomNumber = 1;
                repo = new TreeMap<>();
            }
        } else {
            nextRoomNumber = 1;
            repo = new TreeMap<>();
            writeFile();
        }
    }

    private void writeFile() {
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeLong(nextRoomNumber);
            oo.writeObject(repo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Room createRoom(String type,String capacity, String amenities , double price ) {
        if ( type == null || type.isBlank() || capacity == null
                || capacity.isBlank() || amenities == null || amenities.isBlank() || price < 0.0) return null;
        String id = String.format("C%03d", nextRoomNumber);
        if (repo.containsKey(id)) return null ;
        Room room = new Room (id,type,capacity,amenities,price);
        repo.put(id ,room) ;
        ++nextRoomNumber;
        return room ;
    }

    @Override
    public Room retrieveRoom(String roomNumber) {
        if (roomNumber == null) return null;
        return repo.get(roomNumber);
    }

    public Map<String, Room> getAllRoom(){
        Map<String, Room> roomMap = new HashMap<>();
        for (Room room : repo.values()) {
            roomMap.put(room.getRoomNumber(), room);
        }
        return roomMap;
    }

    @Override
    public boolean updateRoom(Room room) {
        if (room == null) return false;
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
