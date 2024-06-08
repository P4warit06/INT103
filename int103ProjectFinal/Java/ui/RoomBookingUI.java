package ui;

import domain.Person;
import domain.Room;
import repository.memory.*;

import service.Service;

import java.io.Console;
import java.util.Arrays;
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
                1. Yes, bring me to login.
                2. No, I want to create an account.
                3. Exit
                your choice is [1-4] : 
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
                    case 1 -> uiLogin();
                    case 2 -> uiNewPerson();
                    case 3 -> System.out.println("Exit");
                }
                    break;
                } else {
                    System.out.println(startMenu);
                }
            }
        }

    private void uiLogin() {
        Scanner sc = new Scanner(System.in);
        Console c = System.console();
        String email;
        char[] password; // Use char[] for password
        String uiLoginMenu = """
                                
                Do you want to Login?
                1. Login!
                2. No, back to start menu.
                your choice is [1-2] : 
                """;
        System.out.print(uiLoginMenu);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(uiLoginMenu);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("1|2")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> {
                        // Email input and validation
                        do {
                            System.out.print("Enter your email: ");
                            email = sc.nextLine().trim();
                            if (email.isBlank()) {
                                System.out.println("Email cannot be empty. Please try again.");
                            }
                        } while (email.isBlank());

                        // Password input and validation
                        do {
                            System.out.print("Enter your password: ");
                            if (c != null) {
                                password = c.readPassword(); // Read directly into char[]
                            } else {
                                password = sc.nextLine().toCharArray(); // Convert String to char[]
                            }

                            if (password.length == 0 || new String(password).contains(" ")) { // Check for empty or spaces
                                System.out.println("Password cannot be empty or contain spaces. Please try again.");
                                // Clear the password array after each failed attempt
                                Arrays.fill(password, ' ');
                            }
                        } while (password.length == 0 || new String(password).contains(" "));

                        // Login validation (update your service method accordingly)
                        Person user = service.getLoginPerson(email, Arrays.toString(password));
                        if (user != null) {
                            System.out.println("Login successful! Welcome, " + user.getName() + "!");
                            uiViewMenu();
                        } else {
                            System.out.println("Login failed. Invalid email or password.");
                            uiLogin();
                        }

                        // Clear the password array after login attempt
                        Arrays.fill(password, ' ');
                    }
                    case 2 -> start();
                }
                break;
            } else {
                System.out.println(uiLoginMenu);
            }

        }
    }

    private void uiViewMenu() {
        var sc = new Scanner(System.in);
        String viewMenu = """
            Welcome to the Room Booking Menu!
            1. Make a new reservation
            2. View my reservations
            3. Cancel a reservation
            4. Exit
            Your choice is [1-4]: """;
        System.out.print(viewMenu);
             while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.isBlank()){
                System.out.println(viewMenu);
                continue;
            }
           Scanner ans = new Scanner(line);

            ans.useDelimiter("\\n");

            if (ans.hasNext("[1|2|3|4]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1:
                        uiMakeReservation();
                        break;
                    case 2:
                        uiViewReservations();
                        break;
                    case 3:
                        uiCancelReservation();
                        break;
                    case 4:
                        System.out.println("Exiting to Start Menu...");
                        start();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                uiViewMenu();
            }
        }
    }

    private void uiCancelReservation() {
    }

    private void uiViewReservations() {

    }

    private void uiMakeReservation() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Make a new reservation");
//        String makeReservation = """
//                1. Make a new reservation
//                2. Exit
//                Your choice is [1-2]: """;
//        System.out.print(makeReservation);
//            while (sc.hasNextLine()) {
//                String line = sc.nextLine();
//                if (line.isBlank()) {
//                    System.out.println(makeReservation);
//                    continue;
//                }
//                Scanner ans = new Scanner(line);
//                ans.useDelimiter("\\n");
//                if (ans.hasNext("[1|2]")) {
//                    int i = ans.nextInt();
//                    switch (i) {
//                        case 1:
//                            service.createRoomReservation();
//                            break;
//                        case 2:
//                            uiViewMenu();
//                            break;
//                }
//                    break;
//                } else {
//                    System.out.println(makeReservation);
//                }
//            }

    }


    private void uiNewPerson() {
        Scanner sc  = new Scanner(System.in);
        Console c = System.console();
        sc.useDelimiter("\\n");
        System.out.println("Registration");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

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

