package Tenmo;

import Tenmo.services.AuthenticationService;
import Tenmo.services.ConsoleService;
import Tenmo.services.UserService;
import ch.qos.logback.core.net.server.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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

				authenticationService.login(username, password);
				// some kind of loop until login succeeds
				userChoice = -1;
				while (userChoice != 0){
					consoleService.printAccountMenu();
					userChoice = consoleService.promptForMenuSelection("Select an option: ");
					if(userChoice == 1){
						//getBalance
					} else if (userChoice == 2){
						//deposit
					}else if (userChoice == 3){
						//send
					}else if (userChoice == 4){
						//checkPending
					}else if (userChoice == 5){
						//makeRequest
					}else if (userChoice == 6){
						//backToLogin
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
