package ui;

import domain.Person;

import domain.Reservation;
import domain.Room;
import service.Service;

import java.io.Console;
import java.util.Scanner;

public class RoomBookingUI {
    private final Service service;
    private Person customerPerson;

    public RoomBookingUI(boolean fromFile, Service serviceStart) {
        /*if fromFile is true , load/save Person into file*/
        service = serviceStart;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        String startMenu = """
                Welcome to Room Booking Application !
                Do you have an account?
                1. Yes, bring me to login.
                2. No, I want to create an account.
                3. Exit
                your choice is [1-3] : 
                """;
        System.out.print(startMenu);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(startMenu);
                continue;
            }
            Scanner ans = new Scanner(line);
//            ans.useDelimiter("\\n");
            if (ans.hasNext("1|2|3")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> uiLogin();
                    case 2 -> uiRegisterPerson();
                    case 3 ->  System.out.println("Exit");

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

        String LoginMenu = """
                                
                Do you want to Login?
                1. Login!
                2. No, back to start menu.
                your choice is [1-2] : 
                """;
        System.out.print(LoginMenu);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(LoginMenu);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("1|2|3")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> {
                        System.out.println("Enter your email: ");
                        String email = sc.nextLine();
                        System.out.println("Enter your password: ");
                        String password = sc.nextLine();
                        customerPerson = service.getLoginPerson(email, password);
                        if (customerPerson == null) {
                            System.out.println("Login Failed");
                            start();
                        } else {
                            System.out.println("Login Successfully");
                            System.out.println("Hello " + customerPerson.getName() + "!");
                            uiViewMenu();
                        }

                    }

                    case 2 -> start();
                }
                break;
            } else {
                System.out.println(LoginMenu);
            }
        }
    }

    private void uiRegisterPerson() {
        Scanner sc = new Scanner(System.in);
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
        service.registerPerson(name, email, password);
        System.out.println("Register Successfully!");
        start();
    }


    private void uiViewMenu() {
        var sc = new Scanner(System.in);
        String viewMenu = """
                Welcome to the Room Booking Menu!
                1. List all rooms
                2. Make a new reservation
                3. View my reservations
                4. Cancel a reservation
                5. Get my balance
                6. Exit
                Your choice is [1-6]: """;
        System.out.print(viewMenu);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.println(viewMenu);
                continue;
            }
            Scanner ans = new Scanner(line);

            ans.useDelimiter("\\n");

            if (ans.hasNext("^[1-6]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1:
                        uiListAllRooms();
                        break;
                    case 2:
                        uiMakeReservation();
                        break;
                    case 3:
                        uiViewReservations();
                        break;
                    case 4:
                        uiCancelReservation();
                        break;
                    case 5:
                        System.out.println("Your balance is "+ customerPerson.getBalance());
                        uiViewMenu();
                        break;
                    case 6:
                        System.out.println("Back to Start Menu...");
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

    private void uiListAllRooms() {
        var rooms = service.getAllRooms();
        for (var roomMap : rooms.entrySet()) {
            var room = roomMap.getValue();
            System.out.print("Room Number [" + room.getRoomNumber());
            System.out.print("] Type [" + room.getType());
            System.out.print("] Capacity [" + room.getCapacity());
            System.out.print("] Amenities [" + room.getAmenities());
            System.out.print("]  Price [" + room.getPrice());
            System.out.print("]  Room Available [" + room.isAvailable() + "]");
            System.out.println();
        }
        uiViewMenu();

    }

    private void uiMakeReservation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to make a new reservation?");
        String makeReservation = """
                1. Make a new reservation
                2. Back to menu
                Your choice is [1-2]: """;
        System.out.print(makeReservation);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.println(makeReservation);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("[1|2]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1:
                        System.out.println("Enter room number you want to reserve: ");
                        String roomNumber = sc.nextLine();
                        if (checkLogin()) {
                            start();
                        }
                        Room room = service.getRoom(roomNumber);
                        if (room == null) {
                            System.out.println("room " + roomNumber + " not found");
                            uiMakeReservation();
                        } else if (!room.isAvailable()) {
                            System.out.println("room " + roomNumber + " unavailable");
                            uiMakeReservation();
                        }

                        Reservation reservation = service.createRoomReservation(customerPerson, room);
                        if(reservation != null){
                            System.out.println("Reservation made successfully!");
                            System.out.println("your reservation id is " + reservation.getReservationID());
                        }
                        uiViewMenu();
                        break;
                    case 2:
                        uiViewMenu();
                        break;
                }
                break;
            } else {
                System.out.println(makeReservation);
            }
        }

    }

    private void uiViewReservations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to see your reservation ?");
        String viewReservation = """
                1. View my reservation!
                2. Back to menu
                Your choice is [1-2]: """;
        System.out.print(viewReservation);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.println(viewReservation);
                continue;
            }
            Scanner ans = new Scanner(line);
            if (ans.hasNext("[1|2]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> {
                        System.out.println("List all your reservations...");
                        var myReservation = service.getMyReservation(customerPerson.getPersonId());
                        for (var reservation : myReservation) {

                            System.out.print("Reservation ID [" + reservation.getReservationID());
                            System.out.print("] Room number [" + reservation.getRoom().getRoomNumber()+ "]");
                            System.out.println();
                        }
                        uiViewMenu();
                    }
                    case 2 -> uiViewMenu();
                }
                break;
            } else {
                System.out.println(viewReservation);
            }
        }
    }

    private void uiCancelReservation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to cancel your reservation ?");
        String cancelReservation = """
                1. Cancel a reservation!
                2. Back to menu
                Your choice is [1-2]: """;
        System.out.print(cancelReservation);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.println(cancelReservation);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("[1|2]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1:
                        //Cancel a reservation
                        System.out.println("Enter reservation ID you want to cancel: ");
                        String reservationID = sc.nextLine();
                        Reservation reservation = service.getReservationById(reservationID);
                        if(reservation == null){
                            System.out.println("reservation id "+ reservationID + " not found");
                            uiCancelReservation();
                        }

                        if(!customerPerson.getPersonId().equals(reservation.getPerson().getPersonId())){
                            System.out.println("reservation id "+ reservationID + " not found");
                            uiCancelReservation();
                        }
                        service.cancelReservation(reservation, customerPerson);
                        uiViewMenu();
                        break;
                    case 2:
                        uiViewMenu();
                        break;
                }
                break;
            } else {
                System.out.println(cancelReservation);
            }
        }
    }

    private boolean checkLogin() {
        if (customerPerson == null) {
            System.out.println("cannot to proceed go to login");
            return true;
        }
        return false;
    }
}

