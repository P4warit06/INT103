package service;

import domain.*;
import repository.*;
import repository.memory.InMemoryPersonRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

public class Service {
    private final PaymentRepository paymentRepo;
    private final PersonRepository personRepo;
    private final ReservationRepository reservationRepo;
    private final RoomRepository roomRepo;

    public Service(PaymentRepository paymentRepo, PersonRepository personRepo, ReservationRepository reservationRepo, RoomRepository roomRepo) {
        this.paymentRepo = paymentRepo;
        this.personRepo = personRepo;
        this.reservationRepo = reservationRepo;
        this.roomRepo = roomRepo;
    }

    public Room createRoom(String type, String capacity, String amenities, double price) {
        return roomRepo.createRoom(type, capacity, amenities, price);
    }

    public Room getRoom(String roomNumber) {
        return roomRepo.retrieveRoom(roomNumber);
    }

    public boolean updateRoom(Room room) {
        return roomRepo.updateRoom(room);
    }

    public boolean checkRoomAvailable(String roomNumber) {
        var room = roomRepo.retrieveRoom(roomNumber);
        return room.isAvailable();
    }

    public Reservation createRoomReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        return reservationRepo.createReservation(person, room, status, checkInDate, checkOutDate);
    }

    public void registerPerson(String name, String email, String password) {
        personRepo.createPerson(name, email, password);
    }


    public Person getLoginPerson(String email,String password) {
        return personRepo.retrievePerson(email);
    }

        public boolean updatePerson (Person person){
            return personRepo.updatePerson(person);
        }

        public boolean deletePerson (Person person){
            return personRepo.deletePerson(person);
        }

        public Reservation getReservationById (String reservationId){
            return reservationRepo.retrieveReservation(reservationId);
        }

        public LocalDate getPersonCheckInDate (String reservationId){
            var reservation = reservationRepo.retrieveReservation(reservationId);
            if (reservation == null) return null;
            return reservation.getCheckInDate();
        }

        public LocalDate getPersonCheckOutDate (String reservationId){
            var reservation = reservationRepo.retrieveReservation(reservationId);
            if (reservation == null) return null;
            return reservation.getCheckOutDate();
        }
        public boolean cancelRoomReservation (Reservation reservationDelete){
            return reservationRepo.deleteReservation(reservationDelete);
        }

        public Payment roomReservationPayment (Reservation reservation,double amount, String method, String status){
            return paymentRepo.createPayment(reservation, amount, method, status);
        }

    }
