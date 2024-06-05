package testDomain;

import domain.Payment;
import domain.Person;
import domain.Reservation;
import domain.Room;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        testPerson();
        testRoom();
        testPayment();
        testReservation();
    }

    public static void testPerson() {
        System.out.println("### Test Person ###");
        //Create Room
        Room r1 = new Room("1", "airConditional", "2 people", "not have", 3500.00);
        Room r2 = new Room("2", "airConditional", "2 people", "have", 4500.00);
        //Create Person
        Person p1 = new Person("1", "Ohm", "ohm.chonburi@mail.kmutt.ac.th", "1150");
        Person p2 = new Person("2", "Vee", "vee.minburi@mail.kmutt.ac.th", "191");
        Person p3 = new Person("3", "Smark", "smark.Sakhon@mail.kmutt.ac.th", "monkey");
        Person p4 = new Person("4", "Ice", "ice.chonburi@mail.kmutt.ac.th", "RTX3090");
        Person p5 = new Person("5", "Smill", "smill.Sakhon@mail.kmutt.ac.th", "smill.sakhon");
        Person p6 = new Person("5", "IDontKnow", "Eiei@mail.kmutt.ac.th", "Kai");
        //test toString
        System.out.println("## Test toString ##");
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        System.out.println(p4.toString());
        System.out.println(p5.toString());
        //test getter setter
        //Ohm
        System.out.println("## Test Getter And Setter ##");
        System.out.println(p1.getName());
        p1.reName("OhmChonburi");
        System.out.println(p1.getName());
        //Vee
        System.out.println(p2.getName());
        p2.reName("VeeMinburi");
        System.out.println(p2.getName());
        //test compareTo
        System.out.println("## Test CompareTo ##");
        System.out.println(p3.compareTo(p4)); //Not equal
        System.out.println(p5.compareTo(p6)); //Equal
        //test rePassword
        System.out.println("## Test RePassword ##");
        p1.rePassword("ohm.chonburi@mail.kmutt.ac.th", "1234");
        System.out.println("New password: " + p1.getPassword());
        try {
            p1.rePassword("ohm.chonburi@mail.com", "1234");
        } catch (Exception e) {
            System.out.println("Email not equal");
        }
        //test reservation
//        System.out.println(r1.isAvailable());
//        System.out.println(p1.reservation(r1));
//        System.out.println(r1.isAvailable());

    }
    public static void testRoom() {
        System.out.println();
        System.out.println("### Test Room ###");
        //Create Room
        Room r11 = new Room("11", "airConditional, Big", "4 people", "have", 6000.00);
        Room r22 = new Room("22", "airConditional, Small", "1 people", "have", 3000.00);
        //test toString
        System.out.println("## test toString ##");
        System.out.println(r11.toString());
        System.out.println(r22.toString());
        //test Getter Setter
        System.out.println(r11.getPrice());
        r11.setPrice(100000.00);
        System.out.println(r11.getPrice());
    }
    public static void testPayment() {
        System.out.println("### Test Payment ###");
        //Objects Room
        Room r111 = new Room("111", "airConditional, Big", "4 people", "have", 6000.00);
        //Object Person
        Person p6 = new Person("00", "IDontKnow", "Eiei@mail.kmutt.ac.th", "Kai");
        //Object Reservation
        Reservation re1 = new Reservation("1", p6, r111, r111, LocalDate.of(2024,6,6), LocalDate.of(2025,6,6));
        //Create Payment
        Payment pm1 = new Payment("1", re1, 6000.00, "scan", "Not yet paid");
        //test toString
        System.out.println(pm1.toString());
        System.out.println(r111.isAvailable());
        //test getter setter
        System.out.println(pm1.getAmount());
        pm1.setAmount(7000.0);
        System.out.println(pm1.getAmount());
    }
    public static void testReservation() {
        //Object Room
        Room r6 = new Room("6", "airConditional, SuperBig", "6 people", "have", 18000.00);
        System.out.println(r6.isAvailable());
        //Object Person
        Person p6 = new Person("6", "Job", "Job@email.kmutt.ac.th", "Roi Et");
        //Create Reservation
        Reservation re6 = new Reservation("6", p6, r6, r6, LocalDate.of(2024,6,6), LocalDate.of(2025,6,6));
        System.out.println(r6.isAvailable());
        //test toString
        System.out.println(re6.toString());
        //test Getter Setter
        System.out.println(re6.getCheckOutDate());
        re6.setCheckOutDate(LocalDate.of(2026,6,6));
        System.out.println(re6.getCheckOutDate());

    }
}
