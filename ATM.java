package assignment1_DS;

import java.rmi.Naming;
import java.rmi.registry.*;
import java.util.Scanner;
// this class is responsible for handling the client side
public class ATM {

public static void main (String args[]) throws Exception {
	BankInterface bk;
	Scanner scanner = new Scanner(System.in);
	try {
		// sets up the registry
		Registry registry = LocateRegistry.getRegistry();
		//looks for the bankInterface reference
         bk = (BankInterface) registry.lookup("BankObjectReference");
         // once found it prints a message saying it got the client
        System.out.println("Client Binded!\n\n\n");
        ATMHelper helper = new ATMHelper();
        // welcomes user to the bank
        helper.StartUpScreen();
        
        // while user doesnt type log off the session is still active
        while(!(scanner.nextLine().contains("logoff"))) {
        	System.out.println("next command:");
        	String line = scanner.nextLine();
        	String Action = helper.getAction(line);
        	// gets user input and passes it to atmHelper class to act upon
            helper.sortLine(bk,Action,line);
        }
        System.out.println("System Finished");
        
    } catch (Exception e) {
        System.err.println("exception:");
        e.printStackTrace();
    }
	
	
}

}