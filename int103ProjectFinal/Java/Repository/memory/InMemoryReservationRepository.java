package repository.memory;

import repository.ReservationRepository;
import domain.Person;
import domain.Reservation;
import domain.Room;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryReservationRepository implements ReservationRepository {
    private long nextReservationId = 1;
    private final Map<String,Reservation> repo;

    public InMemoryReservationRepository() { repo = new TreeMap<>(); }
    @Override
    public Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        if (person == null || room == null || status == null || checkInDate == null || checkOutDate == null) return null;
        String number = String.format("A%011d", nextReservationId);
        if (repo.containsKey(number)) return null;
        Reservation reservation = new Reservation(number,person,room,status,checkInDate,checkOutDate);
        repo.put(number,reservation);
        ++nextReservationId;
        return reservation;
    }

    @Override
    public Reservation retrieveReservation(String number) {
        if (number==null||number.isBlank()) return null;
        return repo.get(number);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null) return false;
        repo.replace(reservation.getReservationID(),reservation); // status , id
        return true;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        if (reservation== null) return false;
        repo.remove(reservation.getReservationID(),reservation);
        return true;
    }

    @Override
    public Stream<Reservation> stream() { return repo.values().stream(); }
}

