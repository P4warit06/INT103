package Repository.file;

import Repository.ReservationRepository;
import domain.Payment;
import domain.Person;
import domain.Reservation;
import domain.Room;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FileReservationRepository implements ReservationRepository {
    private String filename = "reservation.dat";
    private long nextReservationId;
    private Map<String, Reservation> repo;

    public FileReservationRepository() {
        File fileReservation = new File(filename);
        if (fileReservation.exists()) {
            try(FileInputStream fi = new FileInputStream(fileReservation);
                BufferedInputStream bi = new BufferedInputStream(fi);
                ObjectInputStream oi = new ObjectInputStream(bi)) {
                nextReservationId = oi.readLong();
                repo = (Map<String, Reservation>) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                nextReservationId = 1;
                repo = new TreeMap<>();
            }
        } else {
            nextReservationId = 1;
            repo = new TreeMap<>();
        }
    }

    @Override
    public Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        if (person == null || room == null || status == null || checkInDate == null || checkOutDate == null) return null;
        String reservationId = "ReservationId: " + ++nextReservationId;
        if (repo.containsKey(reservationId)) return null;
        Reservation reservation = new Reservation(reservationId, person, room, status, checkInDate, checkOutDate);
        repo.put(reservationId, reservation);
        return reservation;
    }

    @Override
    public Reservation retrieveReservation(String number) {
        if (number == null || number.isBlank()) return null;
        return repo.get(number);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null) return false;
        repo.replace(reservation.getReservationID(), reservation);
        return true;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        if (reservation == null) return false;
        repo.remove(reservation.getReservationID(), reservation);
        return true;
    }

    @Override
    public Stream<Reservation> stream() {
        return repo.values()
                .stream()
                .filter(Objects::nonNull);
    }
}
