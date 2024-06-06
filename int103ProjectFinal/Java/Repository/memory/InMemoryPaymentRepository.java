package Repository.memory;

import Repository.PaymentRepository;
import domain.Payment;
import domain.Reservation;
import java.util.Map;
import java.util.TreeMap;

public class InMemoryPaymentRepository implements PaymentRepository {
    private long nextPaymentId =1;
    private final Map<String, Payment> repo;
    public InMemoryPaymentRepository() { repo = new TreeMap<>(); }


    @Override
    public Payment createPayment(Payment payment, Reservation reservation) {
        if (payment == null) return null;
        var number = String.format("A%011d", nextPaymentId);
        if (repo.containsKey(number)) return null;
        var Payment = new Payment(payment.getPaymentId(),reservation, payment.getAmount(),payment.getMethod(),payment.getStatus());
        repo.put(number, payment);
        ++nextPaymentId;
        return payment;
    }

    @Override
    public Payment retrievePayment(String number) {
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

}
