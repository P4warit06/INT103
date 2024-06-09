package ui;

import domain.Person;
import repository.database.*;
import repository.file.*;
import repository.memory.*;
import service.Service;


import java.util.Scanner;

public class ChooseDataKeeper {
    private Service dataKeeperService;

    public ChooseDataKeeper(Service dataKeeperService) {
        this.dataKeeperService = dataKeeperService;
    }

    public void uiChooseDataKeeper() {
        Scanner sc = new Scanner(System.in);
        String chooseDataKeeperMenu = """
                Welcome to Room Booking System !
                Please choose your data keeper:
                1. Memory
                2. File
                3. Database
                4. Exit
                your choice is [1-4]: """;
        System.out.print(chooseDataKeeperMenu);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(chooseDataKeeperMenu);
                continue;
            }
            Scanner ans = new Scanner(line);
//            ans.useDelimiter("\\n");
            if (ans.hasNext("1|2|3|4")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> {
                    dataKeeperService = new Service(new InMemoryPaymentRepository(),
                            new InMemoryPersonRepository(),
                            new InMemoryReservationRepository(),
                            new InMemoryRoomRepository()
                            );
                    var roomBookingUIFromMemory = new RoomBookingUIFromMemory(dataKeeperService);
                    roomBookingUIFromMemory.start();
                    }
                    case 2 -> {
                        dataKeeperService = new Service(new FilePaymentRepository(),
                                new FilePersonRepository(),
                                new FileReservationRepository(),
                                new FileRoomRepository()
                        );
                        var roomBookingUIFromFile = new RoomBookingUIFromFile(dataKeeperService);
                        roomBookingUIFromFile.start();
                    }
                    case 3 -> {
                        dataKeeperService = new Service(
                                new DatabasePaymentRepository(),
                                new DatabasePersonRepository(),
                                new DatabaseReservationRepository(),
                                new DatabaseRoomRepository()
                        );
                        var roomBookingUIFromDatabase = new RoomBookingUIFromDatabase(dataKeeperService);
                        roomBookingUIFromDatabase.start();
                    }
                    case 4 -> {System.out.println("Exit");
                        System.exit(0);
                    }
                }
                break;
            } else {
                System.out.println("Please choose 1-4");
                System.out.println(chooseDataKeeperMenu);
            }
        }
    }
}
