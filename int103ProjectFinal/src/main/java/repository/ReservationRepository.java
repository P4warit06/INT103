package repository;

import domain.Person;
import domain.Reservation;
import domain.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface ReservationRepository  {
    Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate);
    Reservation createReservation(Person person, Room room);
    Reservation retrieveReservation(String number);
    boolean updateReservation(Reservation reservation);
    boolean deleteReservation(Reservation reservation);
    public Stream<Reservation> stream();
    List<Reservation> getReservation(String personalId);
}