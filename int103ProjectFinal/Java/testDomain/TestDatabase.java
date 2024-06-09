package testDomain;

import domain.Person;
import repository.PersonRepository;
import repository.database.DatabasePaymentRepository;
import repository.database.DatabasePersonRepository;
import repository.database.DatabaseReservationRepository;
import repository.database.DatabaseRoomRepository;
import repository.memory.InMemoryPaymentRepository;
import repository.memory.InMemoryPersonRepository;
import repository.memory.InMemoryReservationRepository;
import repository.memory.InMemoryRoomRepository;
import service.Service;
import ui.ChooseDataKeeper;

public class TestDatabase {
    public static void main(String[] args) {
        Service fromDatabase = new Service(
                new DatabasePaymentRepository(),
                new DatabasePersonRepository(),
                new DatabaseReservationRepository(),
                new DatabaseRoomRepository()
        );
        testDatabase();
        testChooseDataKeeper(fromDatabase);
//        initialRoom(fromDatabase);
//        initialPerson(fromDatabase);
    }

    private static void testChooseDataKeeper(Service fromDatabase) {
        System.out.println("### Test Choose Data Keeper From Database ###");
        ChooseDataKeeper keeper = new ChooseDataKeeper(fromDatabase);
        keeper.uiChooseDataKeeper();
    }



    private static void initialRoom(Service service){
        var obj1 = service.createRoom("airConditional, SuperBig", "6 people", "have", 18000.00);
        var obj2 = service.createRoom("airConditional, SuperBig", "6 people", "have", 18000.00);
        System.out.println("## Check Room 6##" + service.checkRoomAvailable(obj1.getRoomNumber()));
        System.out.println("## Check Room 7##" + service.checkRoomAvailable(obj2.getRoomNumber()));
    }
    private static void initialPerson(Service service){
        service.registerPerson("Ohm", "ohm.chonburi@mail.kmutt.ac.th", "1150");
        service.registerPerson("wee", "vee.minburi@mail.kmutt.ac.th", "191");
        service.registerPerson("Smark", "smark.Sakhon@mail.kmutt.ac.th", "monkey");
        service.registerPerson("Ice", "ice.chonburi@mail.kmutt.ac.th", "RTX3090");
        service.registerPerson("Smill", "smill.Sakhon@mail.kmutt.ac.th", "smill.sakhon");
        service.registerPerson("IDontKnow", "Eiei@mail.kmutt.ac.th", "Kai");
        service.registerPerson("1", "1", "1");

    }

    private static void testDatabase() {
        PersonRepository repository = new DatabasePersonRepository();
        System.out.println("Create Person ------------------------------------------");
        Person newPerson = repository.createPerson("John Doe", "john.doe@example.com", "password123");

        System.out.println("");

        System.out.println("Retrieve Person --------------------------------------------------------");
        Person retrievedPerson = repository.retrievePerson("1");
        Person retrievedPerson2 = repository.retrievePerson("3");
        System.out.println();

        System.out.println("Log In -------------------------------------------------------------------");
        Person loggedInPerson = repository.loginPerson("john.doe@example.com", "password123");
        System.out.println();

        System.out.println("Update ------------------------------------------------------------------------");
        System.out.println("Present Name : " + newPerson.getName());
        System.out.println("Present Email : " + newPerson.getEmail());
        newPerson.reName("HOME HALAND");
        newPerson.reEmail("home.haland@example.com");
        System.out.println("New name: " + newPerson.getName());
        System.out.println("New Email: " + newPerson.getEmail());
        repository.updatePerson(newPerson);
        Person updatedPerson = repository.retrievePerson("1");
        System.out.println("Updated retrieved person: " + updatedPerson);

        System.out.println();
        System.out.println("Stream ---------------------------------");
        System.out.println("ID PERSON1: " + newPerson.getPersonId());
        boolean deleted = repository.deletePerson(newPerson);
        if (deleted) {
            System.out.println("Person deleted successfully.");
        } else {
            System.out.println("Failed to delete person.");
        }

        System.out.println("Persons in repository:");
        repository.stream().forEach(System.out::println);
    }
}