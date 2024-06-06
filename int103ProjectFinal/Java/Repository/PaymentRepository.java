package Repository;

import domain.Payment;
import domain.Person;
import domain.Reservation;
import domain.Room;

import java.time.LocalDate;

public interface PaymentRepository {


    Payment createPayment(Payment payment, Reservation reservation);

    Payment retrievePayment(String number);

    boolean updatePayment(Payment payment);

    boolean deletePayment(Payment payment);


}
