package assignment1_DS;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//This class runs the bank server
public class Bank extends UnicastRemoteObject implements BankInterface {
	private static int AccountNumber=0;
	private long LogInToken;
	private static List<Account> accounts=new ArrayList<>(); // users accounts

	// blank constructor
	public Bank() throws RemoteException
	{}
	
	//  the deposit method steps: 1. check token 2. get current user 3. create new balance
	//  4. set the balance 5. create a new transaction object within account
	public void deposit(int account, int amount, long sessionID) throws RemoteException, InvalidSession {
		
		checkToken(sessionID);
		Account UserAccount = getUser(account);
		int balance = UserAccount.getBalance();
			
		balance=balance+amount;
		UserAccount.setBalance(balance);
		UserAccount.setMoneyTransaction("Deposit",amount);
	}

	

	
	//  the withdraw method steps: 1. check token 2. get current user 3. get current balance
	//  4. check it doesnt cause an overdraft 5. set balance 6. create a new transaction object within account
	public void withdraw(int account, int amount,long sessionID) throws RemoteException, InvalidSession {
		
		checkToken(sessionID);
		Account UserAccount = getUser(account);
		int balance = UserAccount.getBalance();
		
		if((balance-amount)<0) {
			throw new InvalidSession("Invalid funds");
		}else {
			balance=balance-amount;
			UserAccount.setBalance(balance);
			UserAccount.setMoneyTransaction("Withdrawal",amount);
		}
	}
	
	

	//  the inquiry method steps: 1. check token 2. get current user 3. get current balance
	//  4. create a new transaction object within account 5. return balance to user
	public int inquiry(int account,long sessionID) throws RemoteException, InvalidSession {
		
		checkToken(sessionID);
		Account UserAccount = getUser(account);
		int balance = UserAccount.getBalance();
		UserAccount.setInquiryTransaction("Enquiry");	
		return balance;
		
	}

	//  the statement method steps: 1. create a new string arraylist to hold transactions 2. check token 
	//  3. get current user 4. Get a transaction object arraylist containing all the user transactions
	//  5. cycle through the transactions and format them to a string 6. return the string arraylist to the user
	public ArrayList<String> getStatement(int account, Date from, Date to,long sessionID) throws RemoteException, InvalidSession {
		
		ArrayList<String> transactionHistory = new ArrayList<>();
		checkToken(sessionID);
		Account UserAccount = getUser(account);
		ArrayList<Transaction> transactions = UserAccount.getTransations();
		for(Transaction action : transactions ) {
			// commented out line to check if date is within bounds
			//if(!(action.getDate().before(from)||action.getDate().after(to))) {
					if(action.getAction().contains("Withdrawal")||action.getAction().contains("Deposit")) {
						String transaction = "DATE:"+action.getDate()+" Action: "+action.getAction()+" Amount: "+action.getAmount();
						transactionHistory.add(transaction);
					}else {
						String transaction = "DATE:"+action.getDate()+" Action: "+action.getAction();
						transactionHistory.add(transaction);
					}
			//}
		}
		
		return transactionHistory;
	}

    //  the login method steps: 1. checks if users first time 2. creates a new login token using currentTime 
	//  3. creates a time out timer for the user session
	//  4. returns token to the user
	@Override
	public long login(String username, String password,boolean FirstTime) throws RemoteException, InvalidLogin {
		// only runs for first time
		if(FirstTime==true) {
			CreateUser(username,password);
			createTimer();
			Date date = new Date();
			LogInToken=date.getTime();
			return LogInToken;
		}
		
		// cycles through user accounts to find username
		else {
		for(Account user : accounts) {
			if(user.getAccountName().equals(username)) {
				if(user.getPassword().equals(password)) {
					Date date = new Date();
					// login successful so Session ID is created and returned
					LogInToken=date.getTime();
					createTimer();
					return LogInToken;
				}else {
					throw new InvalidLogin(true,false);
				}
			}
		}
		}
		// throws an exception as the username was not found
		throw new InvalidLogin(false);
	}

	// logs the user off controlled at the client side
	@Override
	public long logoff(String username, String password) throws RemoteException, InvalidLogin {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// returns the users bank account number
	@Override
	public int getBankAccountNumber(String username) throws RemoteException, InvalidLogin {
		for(Account user : accounts) {
			if(user.getAccountName().equals(username)) {
				return user.getAccountNum();
			}
		}
		return 0;
	}
	
	// cretaes a 5 minute time out for the system
	private void createTimer() {
		TimerTask task = new TimerTask() {	      
			@Override	      
			public void run() {		        
				LogInToken=10;      
				}	    
			};	    	    
			Timer timer = new Timer();	    
			long delay = 300000;	    	    
			// schedules the task to be run after a 5 minute delay	    
			timer.schedule(task, delay);
	}
		
		
	
	// creates a new account object using user details
	private Account CreateUser(String username, String password) {
		Account account = new Account(username,password,AccountNumber++,0);
		accounts.add(account);
		return account;

	}
	
	// private method used to find account objects
	private Account getUser(int AccountNumber) throws InvalidSession {
		
		for(Account user : accounts) {
			if(user.getAccountNum()==AccountNumber) {
				return user;
			}
		}
		//something went wrong
		throw new InvalidSession("UserName not found in system!");
	}
	
	//private method used to check the token for the session id is correct
		private void checkToken(long sessionID) throws InvalidSession {
			if(sessionID==LogInToken) {
				return;
			}
			else {
				throw new InvalidSession("Token identifier invalid");
			}
		}
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception {

		//System.setSecurityManager(new RMISecurityManager());
		try {
			BankInterface bk = new Bank();
			System.setProperty("java.rmi.server.hostname","192.168.56.1");
	            Registry registry = LocateRegistry.getRegistry();
	            registry.rebind("BankObjectReference", bk);
	            System.out.println("BankServer bound");
		} catch (Exception e) {
			System.err.println("exception:");
	        e.printStackTrace();
		}
	}



	


}
