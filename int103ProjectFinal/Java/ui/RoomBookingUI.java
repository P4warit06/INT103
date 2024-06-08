package ui;

import repository.memory.*;

import service.Service;

import java.io.Console;
import java.util.Scanner;

public class RoomBookingUI {
    private final Service service;

    public RoomBookingUI(boolean fromFile) {
        /*if fromFile is true , load/save Person into file*/
        service = new Service(
                new InMemoryPaymentRepository(),
                new InMemoryPersonRepository(),
                new InMemoryReservationRepository(),
                new InMemoryRoomRepository()
                );
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        String startMenu = """
                Welcome to Room Booking Application !
                Do you have an account?
                1. Yes, I have.
                2. No, I want to create an account.
                3. Exit
                your choice is [1-3] : 
                """;
        System.out.print(startMenu);
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(startMenu);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if(ans.hasNext("1|2|3")){
                int i = ans.nextInt();
                switch(i) {
                    case 1 -> uiViewMenu();
                    case 2 -> uiNewCustomer();
                    case 3 -> System.out.println("Exit");
                }
                    break;
                } else {
                    System.out.println(startMenu);
                }
            }
        }

    private void uiViewMenu() {
        
    }


    private void uiNewCustomer() {
        Scanner sc  = new Scanner(System.in);
        Console c = System.console();
        sc.useDelimiter("\\n");
        System.out.println("Registration");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.println(name +" "+ email);

        System.out.print("Enter your password: ");
        String password;
        if (c != null) {
            System.out.println("You have a real console.");
            System.out.println("So, your password will not appear while typing.");
            System.out.print("Please type your password: ");
            password = new String(c.readPassword());
        } else {
            System.out.println("You do not have a real console.");
            System.out.println("So, your password will appear while typing.");
            System.out.print("Please type your password: ");
            password = sc.nextLine();
        }
        System.out.print("Do you want to see what you have just typed in [y/n]: ");
        if (sc.nextLine().equals("y")) {
            System.out.printf("You type: [%s]%n", password);
        }
        service.registerPerson(name,email,password);
        System.out.println("Register Successfully!");
        start();
    }
}
