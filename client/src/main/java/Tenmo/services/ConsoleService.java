package Tenmo.services;

import java.util.Scanner;

public class ConsoleService {

    private Scanner userScan = new Scanner(System.in);


    public void printLoginMenu(){
        System.out.println();
        System.out.println("--------Welcome to Tenmo---------");
        System.out.println("Please login or register to continue...");
        System.out.println();
        System.out.println("(1) Login");
        System.out.println("(2) Register");
        System.out.println("(3) Exit");

    }

    public void printAccountMenu(){
        System.out.println();
        System.out.println("(1) Check Balance");
        System.out.println("(2) Deposit Money");
        System.out.println("(3) Send Money");
        System.out.println("(4) Check Pending Requests");
        System.out.println("(5) Request Money");
        System.out.println("(6) Return to Main Menu");
    }



    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(userScan.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public String promptForString(String prompt) {
        System.out.println(prompt);
        return userScan.nextLine();
    }

    public void printErrorMessage(){
        System.out.println("An error occurred.");
    }
}
