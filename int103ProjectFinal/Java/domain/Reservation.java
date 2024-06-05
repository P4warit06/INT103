package domain;

import exception.InvalidPersonFormatException;
import exception.InvalidReservationFormatException;
import exception.InvalidRoomFormatException;
import org.w3c.dom.css.CSSStyleRule;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private final String reservationID;
    private Person person;
    private Room room;
    private Room status;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(String reservationID, Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        if (reservationID == null || reservationID.isBlank() || person == null || room == null || status == null || !status.isAvailable()) throw new InvalidReservationFormatException();
        this.reservationID = reservationID;
        this.person = person;
        this.room = room;
        status.setAvailable(false);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getReservationID() {
        return reservationID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person == null) throw new InvalidPersonFormatException();
        this.person = person;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        if (room == null) throw new InvalidRoomFormatException();
        this.room = room;
    }

    public boolean getStatus() {
        return status.isAvailable();
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return String.format("Reservation: (reservationID: %s, person: %s, room: %s, status: %s, checkInDate: %s, checkOutDate: %s)", reservationID, person, room, status, checkInDate, checkOutDate);
    }
}
