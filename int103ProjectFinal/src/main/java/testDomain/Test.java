package testDomain;

import repository.memory.InMemoryReservationRepository;
import domain.*;
import repository.memory.InMemoryPaymentRepository;
import repository.memory.InMemoryPersonRepository;
import repository.memory.InMemoryRoomRepository;
import service.*;
import ui.ChooseDataKeeper;
import ui.RoomBookingUIFromMemory;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {

        Service fromMemory = new Service(
                new InMemoryPaymentRepository(),
                new InMemoryPersonRepository(),
                new InMemoryReservationRepository(),
                new InMemoryRoomRepository()
        );
        initialRoom(fromMemory);
        initialPerson(fromMemory);


    RunApplication(fromMemory);

    }

    private static void RunApplication(Service fromMemory) {
        System.out.println("### Test Choose Data Keeper ###");
        ChooseDataKeeper keeper = new ChooseDataKeeper(fromMemory);
        keeper.uiChooseDataKeeper();
    }

    private static void initialRoom(Service service){
        var obj1 = service.createRoom("airConditional, SuperBig", "6 people", "have", 4000.00);
        var obj2 = service.createRoom("airConditional, SuperBig", "6 people", "have", 4000.00);
        var obj3 = service.createRoom("airConditional, Big", "5 people", "have", 3500.00);
        var obj4 = service.createRoom("airConditional, Medium", "4 people", "have", 3000.00);
        var obj5 = service.createRoom("airConditional, Small", "3 people", "have", 2800.00);
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
    }

