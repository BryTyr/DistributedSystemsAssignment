package assignment1_DS;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ATMHelper {
private static Long SessionID = 1L;
	
	public ATMHelper() {}
	
	// ggreats the user
	public void StartUpScreen() {
		System.out.println("Welcome to the Bank");
		System.out.println("rules:\n 1.) all lower case \n 2.) space between each of the entered parameters");
        System.out.println("List of settings:");
        System.out.println("register UserName Password");
        System.out.println("login UserName Password");
        System.out.println("deposit AccountNumber Amount");
        System.out.println("withdraw AccountNumber Amount");
        System.out.println("inquiry AccountNumber");
        System.out.println("statement AccountNumber DateFrom(e.g:23-12-2018) DateTo(e.g:22-01-2019)");
        System.out.println("logoff\n");
        System.out.println("Please log in or register and type in any of the above options\n");
        System.out.println("Press enter to continue\n");
	}

	// case statement to handle what the user would like to do
	public void sortLine(BankInterface bk, String Action, String line) {
		
        switch (Action) {
            case "register":  register(bk,line);
                     break;
            case "login":  login(bk,line);
                     break;
            case "deposit":  deposit(bk,line);
                     break;
            case "withdraw":  withdraw(bk,line);
                     break;
            case "inquiry":  inquiry(bk,line);
                     break;
            case "statement":  satement(bk,line);
                     break;
            case "logoff":  logoff(bk,line);
                     break;
            default: System.out.println("Command entered incorrectly "+Action);
                     break;
        }
        
    }

	private void register(BankInterface bk, String line) {
		String[] words = getWords(line);
		try {
			SessionID = bk.login(words[1],words[2],true);
			System.out.println("register successful! "+SessionID);
			System.out.println("Your new account number is: "+bk.getBankAccountNumber(words[1]));
			System.out.println("press enter button to continue");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InvalidLogin e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	private void login(BankInterface bk, String line) {
		String[] words = getWords(line);
			try {
				SessionID = bk.login(words[1],words[2],false);
				System.out.println("login successful!"+SessionID);
				System.out.println("Your account number is: "+bk.getBankAccountNumber(words[1]));
				System.out.println("press enter button to continue");
			} catch (RemoteException | InvalidLogin e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

	private void inquiry(BankInterface bk, String line) {
		String[] words = getWords(line);
		
			try {
				System.out.println("Your entered values: "+ words[0]+"  "+ words[1]);
				int Balance = bk.inquiry(Integer.parseInt(words[1]), SessionID);
				System.out.println("Your inquiry for acccount "+ words[1]+" was sucessful");
				System.out.println("Your balance is: "+Balance);
				System.out.println("press enter button to continue");
			} catch (NumberFormatException | RemoteException | InvalidSession e) {
				
				System.out.println("Error related to: "+e.getMessage());
				e.printStackTrace();
			}
			
		
		
	}


	private void withdraw(BankInterface bk, String line) {
		String[] words = getWords(line);
		
			try {
				bk.withdraw(Integer.parseInt(words[1]), Integer.parseInt(words[2]), SessionID);
				System.out.println("Withdrawal the sum of "+ words[2]+" from account "+ words[1]);
				System.out.println("New Balance: "+bk.inquiry(Integer.parseInt(words[1]), SessionID));
				System.out.println("press enter button to continue");
			} catch (NumberFormatException | RemoteException | InvalidSession e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}


	private void deposit(BankInterface bk, String line) {
		String[] words = getWords(line);
		try {
			bk.deposit(Integer.parseInt(words[1]), Integer.parseInt(words[2]), SessionID);
			System.out.println("Deposited the sum of "+words[2]+" to account "+words[1]);
			System.out.println("New Balance: "+bk.inquiry(Integer.parseInt(words[1]), SessionID));
			System.out.println("press enter button to continue");
		} catch (NumberFormatException | RemoteException | InvalidSession e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private void satement(BankInterface bk, String line) {
		String[] words = getWords(line);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date datefrom = null,dateto = null;
		try {
			//removes special character ^ from command line
			datefrom = format.parse(words[2]);
			dateto  = format.parse(words[3]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ArrayList<String> transactionHistory = bk.getStatement(Integer.parseInt(words[1]), datefrom, dateto, SessionID);
			for(String transaction : transactionHistory) {
				System.out.println(transaction);
			}
			System.out.println("Press enter button to continue");
		} catch (NumberFormatException | RemoteException | InvalidSession e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private void logoff(BankInterface bk, String line) {
		String[] words = getWords(line);
		SessionID=0l;
		System.out.println("You are about to be logged off, type logoff and press enter to close the system");
	}

	// this two methods parse the command line input of the user
	public String getAction(String line) {
		String words[] =line.split(" ");
		return words[0];
	}
	public String[] getWords(String line) {
		String[] words =line.split(" ");
		return words;
	}
		
}
	
	
	

