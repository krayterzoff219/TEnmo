package Tenmo.services;

import Tenmo.model.Transfer;
import Tenmo.model.User;
import Tenmo.model.UserName;

import java.math.BigDecimal;
import java.util.List;
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
        System.out.println("(5) View All Transfers");
        System.out.println("(6) Request Money");
        System.out.println("(7) Return to Main Menu");
    }

    public void printPendingRequests(Transfer[] requests){
        System.out.println();
        int count = 1;
        for (Transfer request : requests) {
            System.out.println("(" + count + ") \n" + request);
            count++;
        }
        System.out.println();
        System.out.println("(" + count + ") Exit");
    }

    public void printTransfers(Transfer[] allTransfers) {
        System.out.println();
        for(Transfer transfer : allTransfers) {
            System.out.println(transfer);
        }
        System.out.println();
    }


    public void printUserList(UserName[] users){
        System.out.println();
        for(UserName username : users){
            System.out.println("Username: " + username);
        }
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

    public BigDecimal promptForAmount(String prompt) {
        System.out.println(prompt);
        String userInput = userScan.nextLine();
        return new BigDecimal(userInput);
    }

    public void printErrorMessage(){
        System.out.println("An error occurred.");
    }

    public void pause() {
        System.out.println();
        System.out.println("Press enter to continue.");
        userScan.nextLine();
    }
}
