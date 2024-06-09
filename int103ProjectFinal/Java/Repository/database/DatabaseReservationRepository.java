package repository.database;

import domain.Person;
import domain.Reservation;
import domain.Room;
import repository.ReservationRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DatabaseReservationRepository implements ReservationRepository {

    private long nextReservationId = 1;
    private final Map<String,Reservation> repo;

    public DatabaseReservationRepository() { repo = new TreeMap<>(); }
    @Override
    public Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        if (person == null || room == null || status == null || checkInDate == null || checkOutDate == null) return null;
        String roomId = "PersonId: " + nextReservationId;
        if (repo.containsKey(roomId)) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into room(roomNumber,type,capacity,amenities,available,price) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,nextReservationId);
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
        nextReservationId++;
        return room ;
    }

    @Override
    public Reservation createReservation(Person person, Room room) {
        return null;
    }

    @Override
    public Reservation retrieveReservation(String number) {
        return null;
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        return false;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        return false;
    }

    @Override
    public Stream<Reservation> stream() {
        return null;
    }

    @Override
    public List<Reservation> getReservation(String personalId) {
        return null;
    }
}
