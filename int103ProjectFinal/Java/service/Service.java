package service;

import domain.*;
import repository.*;
import java.time.LocalDate;

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

    public boolean checkRoomAvaliable(String roomNumber){
        var room = roomRepo.retrieveRoom(roomNumber);
        return room.isAvailable();
    }

    public Reservation createRoomReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate){
        return reservationRepo.createReservation(person,room,status,checkInDate,checkOutDate);
    }
    public LocalDate getPersonCheckInDate(String reservationId) {
        var reservation = reservationRepo.retrieveReservation(reservationId);
        if(reservation == null)return null;
        return reservation.getCheckInDate();
    }

    public LocalDate getPersonCheckOutDate(String reservationId) {
        var reservation = reservationRepo.retrieveReservation(reservationId);
        if(reservation == null)return null;
        return reservation.getCheckOutDate();
    }
    public boolean cancelRoomReservation(Reservation reservationDelete){
         return reservationRepo.deleteReservation(reservationDelete);
    }

    public Payment roomReservationPayment(Reservation reservation,double amount, String method, String status){
        return paymentRepo.createPayment(reservation,amount,method,status);
    }

}
