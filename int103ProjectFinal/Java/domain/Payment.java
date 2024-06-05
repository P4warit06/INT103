package domain;

import exception.InvalidPaymentFormatException;

import java.io.Serializable;
import domain.*;

public class Payment implements Serializable {
    private final String paymentId;
    private Reservation reservation;
    private double amount;
    private String method;
    private String status;
    public Payment(String paymentId, Reservation reservation, double amount, String method, String status) {
        if (paymentId == null || paymentId.isEmpty() || reservation == null || amount <= 0.0 || method == null || method.isEmpty() ||
                status == null || status.isEmpty()) throw new InvalidPaymentFormatException();
        this.paymentId = paymentId;
        this.reservation = reservation;
        this.amount = amount;
        this.method = method;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Payment: (paymentId: %s, reservation: %s, amount: %s, method: %s, status: %s)", paymentId, reservation, amount, method, status);
    }
}
