package testDomain;

import domain.Person;
import domain.Room;

public class Test {
    public static void main(String[] args) {
        testPerson();
    }

    public static void testPerson() {
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
        System.out.println(r1.isAvailable());
        p1.reservation(r1);
        System.out.println(r1.isAvailable());

    }
}
