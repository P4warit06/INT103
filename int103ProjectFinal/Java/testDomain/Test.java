package testDomain;

import repository.memory.InMemoryReservationRepository;
import domain.*;
import repository.memory.InMemoryPaymentRepository;
import repository.memory.InMemoryPersonRepository;
import repository.memory.InMemoryRoomRepository;
import service.*;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
//        testPerson();
//        testRoom();
//        testPayment();
//        testReservation();
          testService();
    }

    public static void testPerson() {
        System.out.println("### Test Person ###");
        //Create Room
        Room r1 = new Room("1", "airConditional", "2 people", "don't have", 3500.00);
        Room r2 = new Room("2", "airConditional", "2 people", "have", 4500.00);
        //Create Person
        Person p1 = new Person("1", "Ohm", "ohm.chonburi@mail.kmutt.ac.th", "1150");
        Person p2 = new Person("2", "wee", "vee.minburi@mail.kmutt.ac.th", "191");
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
        boolean available = r11.isAvailable();
        System.out.println("r11 is " + available);
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

    public static void testService(){
        System.out.println("++++++++ Test Service ++++++++");
        // Create the service instance
         Service service = null;
         service = new Service(
                 new InMemoryPaymentRepository(),
                 new InMemoryPersonRepository(),
                 new InMemoryReservationRepository(),
                 new InMemoryRoomRepository()
         );

         // Test createRoomReservation method
        Room r6 = new Room("6", "airConditional, SuperBig", "6 people", "have", 18000.00);
        Room r7 = new Room("69", "airConditional, SuperBig", "6 people", "have", 18000.00);
        Room r8 = new Room("100", "airConditional, SuperBig", "6 people", "have", 18000.00);
        //Object Person
        Person p6 = new Person("6", "Job", "Job@email.kmutt.ac.th", "Roi Et");


        //Test createRoomReservation method
        Reservation reservation = service.createRoomReservation(  p6,  r6,  r6, LocalDate.of(2024,6,6), LocalDate.of(2025,6,6));
        if (reservation != null) System.out.println("Reservation created successfully");
        else { System.out.println("Failed to create reservation"); }
        System.out.println("Reservation Id :" + reservation.getReservationID());
        // Test getPersonCheckInDate method
        LocalDate checkInDate = service.getPersonCheckInDate("A00000000001");
        System.out.println("Check-in Date: " + checkInDate);

        // Test getPersonCheckOutDate method
        LocalDate checkOutDate = service.getPersonCheckOutDate("A00000000001");
        System.out.println("Check-out Date: " + checkOutDate);

        // Test checkRoomAvaliable method
        System.out.println("Is Room7 Available: "+ r7.isAvailable());
        System.out.println("Is Room6 Available: "+ r6.isAvailable());

        System.out.println("room7 roomNumber: " + r7.getRoomNumber());
        System.out.println("room6 roomNumber: " + r6.getRoomNumber());

        var obj1 = service.createRoom("airConditional, SuperBig", "6 people", "have", 18000.00);
        var obj2 = service.createRoom("airConditional, SuperBig", "6 people", "have", 18000.00);
        System.out.println("## Check Room 6##" + service.checkRoomAvailable(obj1.getRoomNumber()));
        System.out.println("## Check Room 7##" + service.checkRoomAvailable(obj2.getRoomNumber()));


        service.updateRoom(r6);
        service.updateRoom(r7);
        boolean isRoom6Available = service.checkRoomAvailable(r6.getRoomNumber());
        System.out.println("Is Room6 Available(fn): " + isRoom6Available);
//         Test checkRoomAvaliable method
        boolean isRoom7Availables = service.checkRoomAvailable(r7.getRoomNumber());
        System.out.println("Is Room7 Available(fn): " + isRoom7Availables);







        // Test cancelRoomReservation method
        boolean isCancelled = service.cancelRoomReservation(reservation);
        System.out.println("Reservation cancelled: " + isCancelled);

        // Test roomReservationPayment method
        Payment payment = service.roomReservationPayment(reservation, 200.0, "Credit Card", "Paid");
        if (payment != null) {
            System.out.println("Payment processed successfully");
        } else {
            System.out.println("Failed to process payment");
        }
    }
    }

