package testDomain;

import repository.file.FilePaymentRepository;
import repository.file.FilePersonRepository;
import repository.file.FileReservationRepository;
import repository.file.FileRoomRepository;
import domain.Room;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class TestWriteFile {


    public static void main(String[] args) {
//        testFilePerson();
//        testFileRoom();
        testFilePaymentAndReservation();
    }
    public static void testFilePerson() {
        System.out.println("###Test FilePersonRepo###");
        FilePersonRepository filePersonRepository = new FilePersonRepository();
        //testFileObject
        var obj1 = filePersonRepository.createPerson("Ohm", "ohm@gmail.com", "123456789");
        var obj2 = filePersonRepository.createPerson("Vee", "vee@gmail.com", "12345678");
        var obj3 = filePersonRepository.createPerson("SMark", "smark@gmail.com", "1234567");
        var obj4 = filePersonRepository.createPerson("Ice", "ice@gmail.com", "123456");
        var obj5 = filePersonRepository.createPerson("SMill", "smill@gmail.com", "12345");
        //test retrieve
        System.out.println(filePersonRepository.retrievePerson(obj1.getPersonId()));
        System.out.println(filePersonRepository.retrievePerson(obj2.getPersonId()));
        System.out.println(filePersonRepository.retrievePerson(obj3.getPersonId()));
        System.out.println(filePersonRepository.retrievePerson(obj4.getPersonId()));
        System.out.println(filePersonRepository.retrievePerson(obj5.getPersonId()));
        //test delete
        System.out.println("###Test delete###");
        filePersonRepository.deletePerson(obj1);
        System.out.println(filePersonRepository.retrievePerson(obj1.getPersonId()));
        //test replace
        System.out.println("###Test replace###");
        System.out.println(filePersonRepository.retrievePerson(obj2.getPersonId()));
        obj2.reName("Veeeeee");
        filePersonRepository.updatePerson(obj2);
        System.out.println(filePersonRepository.retrievePerson(obj2.getPersonId()));
        //test stream
        System.out.println("###Test stream###");
        System.out.println(filePersonRepository.stream());

    }
    public static void testFileRoom() {
        System.out.println("###Test FileRoomRepo###");
        FileRoomRepository fileRoomRepository = new FileRoomRepository();
        //testFileObject
        var obj1 = fileRoomRepository.createRoom("Fan", "2 people", "don't have", 3000.00);
        var obj2 = fileRoomRepository.createRoom("Fan", "2 people", "don't have", 3000.00);
        //test retrieve
        System.out.println(fileRoomRepository.retrieveRoom(obj1.getRoomNumber()));
        System.out.println(fileRoomRepository.retrieveRoom(obj2.getRoomNumber()));
        //test delete
        System.out.println("###Test delete###");
        fileRoomRepository.deleteRoom(obj1);
        System.out.println(fileRoomRepository.retrieveRoom(obj1.getRoomNumber()));
        System.out.println(fileRoomRepository.retrieveRoom(obj2.getRoomNumber()));
        //test replace
        System.out.println("###Test replace###");
        var obj3 = fileRoomRepository.createRoom("FanAndAirConditional", "4 people", "have", 6000.00);
        System.out.println(fileRoomRepository.retrieveRoom(obj3.getRoomNumber()));
        obj3.setPrice(10000.00);
        fileRoomRepository.updateRoom(obj3);
        System.out.println(fileRoomRepository.retrieveRoom(obj3.getRoomNumber()));
        //test stream
        System.out.println("###Test stream###");
        System.out.println(fileRoomRepository.stream());
    }
    public static void testFilePaymentAndReservation() {
        System.out.println("###Test FilePaymentRepo###");
        FilePaymentRepository filePaymentRepository = new FilePaymentRepository();
        FilePersonRepository filePersonRepository = new FilePersonRepository();
        FileReservationRepository fileReservationRepository = new FileReservationRepository();
        FileRoomRepository fileRoomRepository = new FileRoomRepository();
        //testFileObject
        var p1 = filePersonRepository.createPerson("Ohm", "ohm@gmailo.com", "123456789");
        var ro1 = fileRoomRepository.createRoom("Fan", "2 people", "don't have", 3000.00);
        var ro2 = fileRoomRepository.createRoom("Fan", "2 people", "don't have", 3000.00);
        var re1 = fileReservationRepository.createReservation(p1, ro1, ro1, LocalDate.of(2024, 6, 8), LocalDate.of(2025, 6, 8));
        var re2 = fileReservationRepository.createReservation(p1, ro2, ro2, LocalDate.of(2024, 6, 8), LocalDate.of(2025, 6, 8));
        var pay1 = filePaymentRepository.createPayment(re1, 3000.0, "Scan", "Not paid");
        var pay2 = filePaymentRepository.createPayment(re1, 3000.0, "Scan", "Not paid");
        //test retrieve
        System.out.println(filePaymentRepository.retrievePayment(pay1.getPaymentId()));
        System.out.println(filePaymentRepository.retrievePayment(pay2.getPaymentId()));
        //test delete
        System.out.println("###Test delete###");
        filePaymentRepository.deletePayment(pay2);
        System.out.println(filePaymentRepository.retrievePayment(pay2.getPaymentId()));
        //test replace
        System.out.println("###Test replace###");
        System.out.println(filePaymentRepository.retrievePayment(pay1.getPaymentId()));
        pay1.setAmount(4000.00);
        filePaymentRepository.updatePayment(pay1);
        System.out.println(filePaymentRepository.retrievePayment(pay1.getPaymentId()));
        //test stream
        System.out.println("###Test stream###");
        System.out.println(fileRoomRepository.stream());

        System.out.println("###Test File Reservation###");
        //test retrieve
        System.out.println(fileReservationRepository.retrieveReservation(re1.getReservationID()));
        System.out.println(fileReservationRepository.retrieveReservation(re2.getReservationID()));
        //test delete
        System.out.println("###Test delete###");
        fileReservationRepository.deleteReservation(re2);
        System.out.println(fileReservationRepository.retrieveReservation(re2.getReservationID()));
        //test replace
        System.out.println("###Test replace###");
        System.out.println(fileReservationRepository.retrieveReservation(re1.getReservationID()));
        re1.setCheckOutDate(LocalDate.of(2024, 6, 9));
        fileReservationRepository.updateReservation(re1);
        System.out.println(fileReservationRepository.retrieveReservation(re1.getReservationID()));
        //test stream
        System.out.println("###Test stream###");
        System.out.println(fileReservationRepository.stream());
    }
}
