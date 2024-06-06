package ui;

import java.util.Scanner;

public class RoomBookingUI {

    private final RoomBookingService service;

    public RoomBookingUI(RoomBookingService service) {
        this.service = service;
    }

    public void start() {

        Scanner sc = new Scanner(System.in);
        String startMenu = """
               1. register a customer
               2. list all customers
               3. list all accounts
               4. exit this menu
               Your answer is [1-4]:""";
        System.out.print(startMenu);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(startMenu);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("[1|2|3|4]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> uiNewCustomer();
                    case 2 -> uiViewCustomer();
                    case 3 -> uiMenuAccount();
                    case 4 -> {System.out.println("Exit");break;}
                }
                break;
            } else {
                System.out.println(startMenu);
            }
        }

    }
    private void uiNewCustomer() {
        Scanner sc = new Scanner(System.in);
        String newCustomer = """
                Enter your name:""";
        System.out.print(newCustomer);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.isBlank()) {
                service.registerCustomer(line);

                break;
            } else {
                System.out.print(newCustomer);
                continue;
            }
        }
    }
    private void uiViewCustomer() {
        Scanner sc = new Scanner(System.in);
        String viewCus = """
                1. open a new account
                2. list all accounts owned by this customer
                3. exit this menu
                Your answer is [1-3]:""";
        System.out.print(viewCus);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.println(viewCus);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("[1|2|3]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> {
                        Scanner scCase1 = new Scanner(System.in);
                        String id = """
                                Enter your Id:""";
                        System.out.print(id);
                        while (scCase1.hasNextLine()) {
                            String lineCase1 = scCase1.nextLine();
                            if (lineCase1.isBlank()) {
                                break;
                            } else {
                                uiNewAccount(service.getCustomer(lineCase1));
                                System.out.println("Open Account Complete");
                            }
                        }
                    }
                    case 2 -> {
                        Scanner scCase2 = new Scanner(System.in);
                        String id = """
                                Enter your id:""";
                        System.out.print(id);
                        while (scCase2.hasNextLine()) {
                            String lineCase2 = scCase2.nextLine();
                            if (lineCase2.isBlank()) {
                                break;
                            } else {
                                uiNewAccount(service.getCustomer(lineCase2));
                            }
                        }
                    }
                    case 3 -> System.out.println("Exit");
                }
                break;
            } else {
                System.out.println(viewCus);
            }
        }
    }
    private void uiNewAccount(Customer customer) {
        if (customer == null) throw new RuntimeException("Customer can't be null");
        service.openAccount(customer.getId());
    }
    private void uiMenuAccount(Customer customer) {
        if (customer == null) throw new RuntimeException("Customer can't null");
        service.getAccountsByCustomer(customer.getId());
    }
    private void uiMenuAccount() {
        uiViewAccount();
    }
    private void uiViewAccount() {
        Scanner sc = new Scanner(System.in);
        String viewMenuAccount = """
                1. deposit
                2. withdraw
                3. close
                4. exit this menu
                Your answer is [1-3]:""";
        System.out.print(viewMenuAccount);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                System.out.print(viewMenuAccount);
                continue;
            }
            Scanner ans = new Scanner(line);
            ans.useDelimiter("\\n");
            if (ans.hasNext("[1|2|3|4]")) {
                int i = ans.nextInt();
                switch (i) {
                    case 1 -> {
                        Scanner scAcc = new Scanner(System.in);
                        String accNum = """
                                Enter your account numbers:""";
                        String amount = """
                                Enter amount:""";
                        System.out.print(accNum);
                        while (scAcc.hasNextLine()) {
                            String readAcc = scAcc.nextLine();
                            if (readAcc.isBlank()) {
                                break;
                            } else {
                                Scanner scAmount = new Scanner(System.in);
                                System.out.print(amount);
                                while (scAmount.hasNextDouble()) {
                                    double num = scAmount.nextDouble();
                                    if (num > 0.0) {
                                        service.deposit(readAcc, num);
                                        System.out.println("Deposit amount: " + num);
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                    case 2 -> {
                        Scanner scAcc = new Scanner(System.in);
                        String accNum = """
                                Enter your account numbers:""";
                        String amount = """
                                Enter amount:""";
                        System.out.print(accNum);
                        while (scAcc.hasNextLine()) {
                            String readAcc = scAcc.nextLine();
                            if (readAcc.isBlank()) {
                                break;
                            } else {
                                Scanner scAmount = new Scanner(System.in);
                                System.out.print(amount);
                                while (scAmount.hasNextDouble()) {
                                    double num = scAmount.nextDouble();
                                    if (num > 0.0) {
                                        service.withdraw(readAcc, num);
                                        System.out.println("Withdraw amount: " + num);
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                    case 3 -> {
                        System.out.println("Close");
                        continue;
                    }
                    case 4 -> {
                        System.out.println("Exit");
                        break;
                    }
                }
                break;
            }
        }
    }
}
