package repository.memory;

import repository.PaymentRepository;
import domain.Payment;
import domain.Reservation;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryPaymentRepository implements PaymentRepository {
    private long nextPaymentId =1;
    private final Map<String, Payment> repo;
    public InMemoryPaymentRepository() { repo = new TreeMap<>(); }


    @Override
    public Payment createPayment(Reservation reservation,double amount, String method, String status) {
        if (reservation == null || amount <0 || method == null || status == null||method.isBlank()||status.isBlank()) return null;
        var number = String.format("A%011d", nextPaymentId);
        if (repo.containsKey(number)) return null;
       Payment payment = new Payment(number,reservation,amount,method,status);
        repo.put(number,payment);
         ++nextPaymentId;
        return payment;
    }

    @Override
    public Payment retrievePayment(String number) {
        if (number==null||number.isBlank())  return null;
        return repo.get(number);
    }

    @Override
    public boolean updatePayment(Payment payment) {
        if (payment == null) return false;
        repo.replace(payment.getStatus(), payment);
        return true;
    }

    @Override
    public boolean deletePayment(Payment payment) {
        if (payment== null) return false;
        return repo.remove(payment.getPaymentId(),payment);
    }

    @Override
    public Stream<Payment> stream() { return repo.values().stream(); }
}
