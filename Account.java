package assignment1_DS;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;

public class Account implements Statement {
private int AccountNum,Balance;
private String UserName,Password;
private Date CreationDate;
private ArrayList<Transaction> transactions= new ArrayList<>();
 
	// account constructor
	public Account(String UserName, String Password,int AccountNum,int Balance) {
		this.UserName=UserName;
		this.Password=Password;
		this.AccountNum=AccountNum;
		this.Balance=Balance;
		Date date = new Date();
		CreationDate=date;
	}
	public String getAccountName() {
		return UserName;
	}
	public String getPassword() {
		return Password;
	}
	public int getAccountNum() {
		return AccountNum;
	}
	public int getBalance() {
		return Balance;
	}
	public void setBalance(int Amount) {
		Balance=Amount;
	}
	public Date getCreationDate() {
		return CreationDate;
	}
	// creates a new transaction object and adds it to the users transaction arraylist
	public void setMoneyTransaction(String Action, int amount) {
		Date date = new Date();
		Transaction action = new Transaction(Action,amount,date);
		transactions.add(action);
	}
	// creates a new transaction object and adds it to the users transaction arraylist
	public void setInquiryTransaction(String Action) {
		Date date = new Date();
		Transaction action = new Transaction(Action,date);
		transactions.add(action);
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Transaction> getTransations() {
		// TODO Auto-generated method stub
		return transactions;
	}
	
}
