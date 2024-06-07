package Repository;

import domain.Payment;
import domain.Person;
import domain.Reservation;
import domain.Room;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface ReservationRepository  {
    Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate);
    Reservation retrieveReservation(String number);
    boolean updateReservation(Reservation reservation);
    boolean deleteReservation(Reservation reservation);
    public Stream<Reservation> stream();
}




