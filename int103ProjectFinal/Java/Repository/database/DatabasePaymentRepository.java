package repository.database;

import domain.Payment;
import domain.Person;
import domain.Reservation;
import repository.PaymentRepository;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DatabasePaymentRepository implements PaymentRepository {
    private long nextPaymentId = 1;
    private final Map<String, Payment> repo;
    public DatabasePaymentRepository() {
        repo = new TreeMap<>();
    }

    @Override
    public Payment createPayment(Reservation reservation, double amount, String method, String status) {
        if (reservation == null || amount < 0.0 || method == null || method.isBlank() || status == null || status.isBlank()) return null;
        String paymentId = "Payment: " + nextPaymentId++;
        if (repo.containsKey(paymentId)) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into payment(paymentID, reservationIID, amount, method, status) values(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, nextPaymentId);
            preparedStatement.setString(2, reservation.getReservationID());
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, method);
            preparedStatement.setString(5, status);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Payment payment = new Payment(paymentId, reservation, amount, method, status);
        repo.put(paymentId, payment);
        nextPaymentId++;
        return payment;
    }

    @Override
    public Payment retrievePayment(String number) {
        if (number == null || number.isBlank()) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM payment WHERE paymentID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1)
                        + " " + resultSet.getString(2)
                        + " " + resultSet.getString(3)
                        + " " + resultSet.getString(4)
                        + " " + resultSet.getString(5)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.get(number);
    }

    @Override
    public boolean updatePayment(Payment payment) {
        if (payment == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "UPDATE payment SET amount = ?, method = ?, status = ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, payment.getAmount());
            preparedStatement.setString(2, payment.getMethod());
            preparedStatement.setString(3, payment.getStatus());
            preparedStatement.executeUpdate();
            repo.replace(payment.getPaymentId(), payment);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deletePayment(Payment payment) {
        if (payment == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "DELETE FROM payment WHERE paymentID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, payment.getPaymentId());
            preparedStatement.executeUpdate();
            return repo.remove(payment.getPaymentId(), payment);
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Stream<Payment> stream() {
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM payment";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Stream.empty();
    }
}