package repository.database;

import domain.Person;
import domain.Reservation;
import domain.Room;
import repository.ReservationRepository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
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
        String reservationId ="";
        Connection con = DatabaseConnection.connect();
        String sql = "insert into reservation(personID,roomID) values(?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getPersonId());
            preparedStatement.setString(2,room.getRoomNumber());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                int newId = rs.getInt(1);
                reservationId = String.valueOf(newId);
            }


        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Reservation reservation = new Reservation(reservationId, person, room);
        return reservation;
    }

    @Override
    public Reservation retrieveReservation(String number) {
        Reservation reservation = null;
        try {
            Connection con = DatabaseConnection.connect();
            String sql = "SELECT * FROM reservation WHERE reservationID = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(number));
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                String sqlPersonal = "SELECT * FROM person WHERE personID = ?";
                PreparedStatement preparedStatementPerson = con.prepareStatement(sqlPersonal);
                preparedStatementPerson.setInt(1, results.getInt(2));
                ResultSet resultsPerson = preparedStatementPerson.executeQuery();
                Person person = null;
                while(resultsPerson.next()) {
                    person = new Person(resultsPerson.getString(1), resultsPerson.getString(2),
                            resultsPerson.getString(3), resultsPerson.getString(4),
                            resultsPerson.getDouble(5));
                }
                
                String sqlRoom = "SELECT * FROM room WHERE roomNumber = ?";
                PreparedStatement preparedStatementRoom = con.prepareStatement(sqlRoom);
                preparedStatementRoom.setInt(1, results.getInt(3));
                ResultSet resultsRoom = preparedStatementRoom.executeQuery();
                Room room = null;
                while(resultsRoom.next()) {
                    room = new Room(resultsRoom.getString(1), resultsRoom.getString(2),
                            resultsRoom.getString(3), resultsRoom.getString(4),
                            resultsRoom.getDouble(6));
                    room.setAvailable(resultsRoom.getBoolean(5));

                }
                reservation = new Reservation(
                        results.getString(1),
                        person,
                        room);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservation;
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
        List<Reservation> reservations = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.connect();
            String sql = "SELECT * FROM reservation WHERE personID = ?";
            String sqlPersonal = "SELECT * FROM person WHERE personID = ?";
            PreparedStatement preparedStatementPerson = con.prepareStatement(sqlPersonal);
            preparedStatementPerson.setInt(1, Integer.parseInt(personalId));
            ResultSet resultsPerson = preparedStatementPerson.executeQuery();
            Person person = null;
            while(resultsPerson.next()) {
                person = new Person(resultsPerson.getString(1), resultsPerson.getString(2),
                        resultsPerson.getString(3), resultsPerson.getString(4),
                        resultsPerson.getDouble(5));
            }
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(personalId));
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {

                String sqlRoom = "SELECT * FROM room WHERE roomNumber = ?";
                PreparedStatement preparedStatementRoom = con.prepareStatement(sqlRoom);
                preparedStatementRoom.setInt(1, results.getInt(3));
                ResultSet resultsRoom = preparedStatementRoom.executeQuery();
                Room room = null;
                while(resultsRoom.next()) {
                    room = new Room(resultsRoom.getString(1), resultsRoom.getString(2),
                            resultsRoom.getString(3), resultsRoom.getString(4),
                            resultsRoom.getDouble(6));
                    room.setAvailable(resultsRoom.getBoolean(5));

                }
                Reservation reservation = new Reservation(
                        results.getString(1),
                        person,
                        room);
                reservations.add(reservation);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservations;
    }
}