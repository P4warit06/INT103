package domain;

import exception.InvalidPersonFormatException;
import exception.InvalidRoomFormatException;
import java.io.Serializable;
public class Person implements Comparable<Person>, Serializable {
    private final String personId;
    private String name;
    private String email;
    private String password;
    private double balance;
    public Person(String personId, String name, String email, String password, double balance) {
        this.balance = balance;
        if (personId == null || personId.isBlank() || name == null || name.isBlank() || email == null || email.isBlank()
                || password == null || password.isBlank()) throw new InvalidPersonFormatException();
        this.personId = personId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public void rePassword(String email, String newPassword) {
        if (email == null || email.isBlank() || !email.equals(this.email) || newPassword == null || newPassword.isBlank()) throw new InvalidPersonFormatException();
        this.password = newPassword;
    }
    //    public String reservation(Room room) {
//        if (room == null) throw new InvalidRoomFormatException();
//        if (room.isAvailable()) {
//            room.setAvailable(false);
//            return "You successfully reserved.";
//        }
//        return "Your reservation was unsuccessful.";
//    } This method is service.
    public String getPersonId() {
        return personId;
    }
    public String getName() {
        return name;
    }
    public void reName(String name) {
        if (name == null || name.isBlank()) throw new InvalidPersonFormatException();
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void reEmail(String email) {
        if (email == null || email.isBlank()) throw new InvalidPersonFormatException();
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return String.format("Person: (PersonId: %s, Name: %s, Email: %s, Password: %s)", personId, name, email, password);
    }
    @Override
    public int compareTo(Person person) {return personId.compareTo(person.personId);}

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
}
