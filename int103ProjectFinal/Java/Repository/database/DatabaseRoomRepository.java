package repository.database;

import domain.Person;
import domain.Room;
import repository.RoomRepository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DatabaseRoomRepository implements RoomRepository {

    private long NextRoomId = 1 ;
    private final Map<String, Room> repo;
    public DatabaseRoomRepository() {repo = new TreeMap<>();}
    @Override
    public Room createRoom(String type, String capacity, String amenities, double price) {
        if ( type == null || type.isBlank() || capacity == null
                || capacity.isBlank() || amenities == null || amenities.isBlank() || price < 0.0) return null;
        String roomId = "RoomNumber: " + NextRoomId;
        if (repo.containsKey(roomId)) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into room(roomNumber,type,capacity,amenities,available,price) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,NextRoomId);
            preparedStatement.setString(2,type);
            preparedStatement.setString(3,capacity);
            preparedStatement.setString(4,amenities);
            preparedStatement.setBoolean(5,true);
            preparedStatement.setDouble(6,price);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Room room = new Room (roomId,type,capacity,amenities,price);
        repo.put(roomId ,room) ;
        NextRoomId++;
        return room ;
    }

    @Override
    public Room retrieveRoom(String roomNumber) {
        if (roomNumber == null || roomNumber.isBlank()) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM room WHERE roomNumber = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, roomNumber);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                System.out.println(results.getString(1)
                        + " " + results.getString(2)
                        + " " + results.getString(3)
                        + " " + results.getString(4)
                        + " " + results.getString(5)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.get(roomNumber);
    }

    @Override
    public Map<String, Room> getAllRoom() {
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM room";
        try {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Room> roomMap = new HashMap<>();
        for (Room room : repo.values()) {
            roomMap.put(room.getRoomNumber(), room);
        }
        return roomMap;
    }

    @Override
    public boolean updateRoom(Room room) {
        if (room == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "UPDATE room SET tyoe = ?, capacity = ?, amenities = ? , price = ?";
        try {
            int affectedRows = 0;
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,room.getType());
            preparedStatement.setString(2,room.getCapacity());
            preparedStatement.setString(3,room.getAmenities());
            preparedStatement.executeUpdate();
            repo.replace(room.getRoomNumber(), room);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean deleteRoom(Room room) {
        if (room  == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "DELETE FROM room WHERE roomNumber = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,room.getRoomNumber());
            preparedStatement.executeUpdate();
            return repo.remove(room.getRoomNumber(), room);
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Stream<Room> stream() {
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM room";
        try {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.values().stream();
    }
}