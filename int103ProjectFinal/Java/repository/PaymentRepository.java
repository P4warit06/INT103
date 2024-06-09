package repository;

import domain.Payment;
import domain.Reservation;

import java.util.stream.Stream;

public interface PaymentRepository {
    Payment createPayment(Reservation reservation, double amount, String method, String status);
    Payment retrievePayment(String number);
    boolean updatePayment(Payment payment);
    boolean deletePayment(Payment payment);
    Stream<Payment> stream();
}
