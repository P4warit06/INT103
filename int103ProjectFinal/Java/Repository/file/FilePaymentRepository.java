package repository.file;

import Repository.PaymentRepository;
import domain.Payment;
import domain.Person;
import domain.Reservation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FilePaymentRepository implements PaymentRepository {
    private String filename = "payment.dat";
    private long nextPaymentId = 0;
    private Map<String, Payment> repo;

    public FilePaymentRepository() {
        File filePayment = new File(filename);
        if (filePayment.exists()) {
            try(FileInputStream fi = new FileInputStream(filePayment);
                BufferedInputStream bi = new BufferedInputStream(fi);
                ObjectInputStream oi = new ObjectInputStream(bi)) {
                nextPaymentId = oi.readLong();
                repo = (Map<String, Payment>) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                nextPaymentId = 1;
                repo = new TreeMap<>();
            }
        } else {
            nextPaymentId = 1;
            repo = new TreeMap<>();
        }
    }

    @Override
    public Payment createPayment(Reservation reservation, double amount, String method, String status) {
        if (reservation == null || amount <= 0.0 || method == null || method.isBlank() || status == null || status.isBlank()) return null;
        String paymentId = "PaymentId: " + nextPaymentId++;
        if (repo.containsKey(paymentId)) return null;
        Payment payment = new Payment(paymentId, reservation, amount, method, status);
        repo.put(paymentId, payment);
        return payment;
    }

    @Override
    public Payment retrievePayment(String number) {
        if (number == null || number.isBlank()) return null;
        return repo.get(number);
    }

    @Override
    public boolean updatePayment(Payment payment) {
        if (payment == null) return false;
        repo.replace(payment.getPaymentId(), payment);
        return true;
    }

    @Override
    public boolean deletePayment(Payment payment) {
        if (payment == null) return false;
        repo.remove(payment.getPaymentId(), payment);
        return true;
    }

    @Override
    public Stream<Payment> stream() {
        return repo.values()
                .stream()
                .filter(Objects::nonNull);
    }
}
