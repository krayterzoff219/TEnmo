package Tenmo;

import Tenmo.model.Transfer;
import Tenmo.model.User;
import Tenmo.services.AuthenticationService;
import Tenmo.services.ConsoleService;
import Tenmo.services.UserService;
import ch.qos.logback.core.net.server.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.List;


public class ClientApplication {

	private final ConsoleService consoleService = new ConsoleService();
	private final UserService userService = new UserService();
	private final AuthenticationService authenticationService = new AuthenticationService();
	public static void main(String[] args) {
		ClientApplication app = new ClientApplication();
		app.run();
	}

	public void run(){
		int userChoice = -1;

		while (userChoice != 1){
			consoleService.printLoginMenu();
			userChoice = consoleService.promptForMenuSelection("Select an option: ");
			String username = consoleService.promptForString("Username: ");
			String password = consoleService.promptForString("Password: ");
			if(userChoice == 2){
				authenticationService.register(username, password);
			}
			if(userChoice == 1){
				String token = null;
				while(token.equals(null)) {
					token = authenticationService.login(username, password);
				}
				userService.setAuthToken(token);
				userChoice = -1;
				while (userChoice != 0){
					consoleService.printAccountMenu();
					userChoice = consoleService.promptForMenuSelection("Select an option: ");
					if(userChoice == 1){
						BigDecimal balance = userService.getBalance();
						System.out.print("Your Current Balance: ");
						System.out.println(balance);
						consoleService.pause();
					} else if (userChoice == 2){
						BigDecimal deposit = consoleService.promptForAmount("Please enter the amount you would like to deposit: ");
						BigDecimal balance = userService.depositMoney(deposit);
						consoleService.pause();
					}else if (userChoice == 3){
						BigDecimal amount = consoleService.promptForAmount("Please enter the amount you would like to transfer: ");
						String receiverName = consoleService.promptForString("Please enter the username of the user you would like to transfer to: ");
						boolean transferSuccessful = userService.sendMoney(amount, receiverName);
						if(transferSuccessful) {
							System.out.println("Transfer Succeeded!");
							System.out.print("Here is your updated balance: ");
							System.out.println(userService.getBalance());
						} else {
							System.out.println("Transfer Failed");
						}
						consoleService.pause();
					}else if (userChoice == 4){
						System.out.println("Your Pending Requests: ");
						List<Transfer> pendingList = userService.viewPendingRequests();
						consoleService.printPendingRequests(pendingList);
						int transferMenuInput = consoleService.promptForMenuSelection("Select a Pending Transfer to Process: ");
						Transfer selectedTransfer = pendingList.get(transferMenuInput -1);
						System.out.println(selectedTransfer);
						int acceptOrReject = consoleService.promptForMenuSelection("1.) Accept 2.) Reject");
						if(acceptOrReject == 1) {
							userService.approveRequest(selectedTransfer.getTransferId());
						} else if(acceptOrReject == 2) {
							userService.rejectRequest(selectedTransfer.getTransferId());
						} else {
							throw new ResourceAccessException("Invalid Menu Selection");
						}
						consoleService.pause();
					} else if(userChoice == 5) {
						System.out.println("Your Transfers: ");
						Transfer[] allTransfers = userService.getTransfersForUser();
						consoleService.printTransfers(allTransfers);
						consoleService.pause();
					} else if (userChoice == 6){
						System.out.println("Users Available to Request Transfer: ");
						User[] users = userService.getUsers();
						consoleService.printUserList(users);
						String selectedUser = consoleService.promptForString("Enter the Username of the Selected User: ");
						BigDecimal amount = consoleService.promptForAmount("Enter the amount you would like to request: ");
						boolean requestSuccess = userService.requestMoney(amount, selectedUser);
						if(requestSuccess){
							System.out.println("Request Successful!");
						} else {
							System.out.println("Request Failed, Please Try Again");
						}
						consoleService.pause();
					}else if (userChoice == 7){
						userChoice = 0;
					}

				}
			}
			if(userChoice == 3){
				break;
			}
		}


	}



	private void handleLogin(){
		String username = consoleService.promptForString("Username: ");
		String password = consoleService.promptForString("Password: ");
		String token = authenticationService.login(username, password);
		if (token != null) {
			userService.setAuthToken(token);
		} else {
			consoleService.printErrorMessage();
		}
	}
}
