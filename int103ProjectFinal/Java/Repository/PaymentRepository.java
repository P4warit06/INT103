package repository;

import domain.Payment;
import domain.Person;
import domain.Reservation;
import domain.Room;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface PaymentRepository {
    Payment createPayment(Reservation reservation, double amount, String method, String status);
    Payment retrievePayment(String number);
    boolean updatePayment(Payment payment);
    boolean deletePayment(Payment payment);
    Stream<Payment> stream();
}
