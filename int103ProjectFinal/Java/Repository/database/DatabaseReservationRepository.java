package repository.database;

import domain.Person;
import domain.Reservation;
import domain.Room;
import repository.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseReservationRepository implements ReservationRepository {

    private long nextReservationId = 1;
    private final Map<String,Reservation> repo;

    public DatabaseReservationRepository() { repo = new TreeMap<>(); }
    @Override
    public Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        if (person == null || room == null || status == null || checkInDate == null || checkOutDate == null) return null;
        String reservationId = "ReservationID: " + nextReservationId;
        if (repo.containsKey(reservationId)) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into reservation(reservationID,personID,roomID,status,checkInDate,checkOutDate) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,nextReservationId);
            preparedStatement.setString(2, person.getPersonId());
            preparedStatement.setString(3,room.getRoomNumber());
            preparedStatement.setBoolean(4,false);
            preparedStatement.setDate(5,Date.valueOf(checkInDate));
            preparedStatement.setDate(6,Date.valueOf(checkOutDate));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Reservation reservation = new Reservation(reservationId,person,room,status,checkInDate,checkOutDate);
        repo.put(reservationId,reservation);
        nextReservationId++;
        return reservation;
    }

    @Override
    public Reservation createReservation(Person person, Room room) {
        if (person == null || room == null) return null;
        String reservationId = "ReservationID: " + nextReservationId;
        if (repo.containsKey(reservationId)) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into reservation(reservationID,personID,roomID,status) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,nextReservationId);
            preparedStatement.setString(2, person.getPersonId());
            preparedStatement.setString(3,room.getRoomNumber());
            preparedStatement.setBoolean(4,false);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        person.setBalance(person.getBalance() - room.getPrice());
        room.setAvailable(false);
        Reservation reservation = new Reservation(reservationId, person, room);
        repo.put(reservationId,reservation);
        nextReservationId++;
        return reservation;
    }

    @Override
    public Reservation retrieveReservation(String number) {
        if (number==null||number.isBlank()) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM reservation WHERE reservationID = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, number);
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
        return repo.get(number);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "UPDATE reservation SET status = ?";
        try {
            int affectedRows = 0;
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1,false);
            preparedStatement.executeUpdate();
            repo.replace(reservation.getReservationID(),reservation);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        if (reservation  == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "DELETE FROM reservation WHERE reservationID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,reservation.getReservationID());
            preparedStatement.executeUpdate();
            repo.remove(reservation.getReservationID(),reservation);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Stream<Reservation> stream() {
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM reservation";
        try {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.values().stream();
    }

    @Override
    public List<Reservation> getReservation(String personalId) {
        Collection<Reservation> values = repo.values();
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM reservations WHERE personID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            Statement statement = con.createStatement();
            preparedStatement.setString(1,personalId);
            ResultSet results = preparedStatement.executeQuery();
            results.next();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.stream()
                .filter(r -> r.getPerson().getPersonId().equals(personalId))
                .collect(Collectors.toList());
    }
}
