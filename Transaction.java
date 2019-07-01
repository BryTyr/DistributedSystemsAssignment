package assignment1_DS;

import java.awt.List;
import java.util.Date;
// transaction object class
public class Transaction{

	private String Action="";
	private Date date;
	private int Amount;
	
	public Transaction(String Action, int Amount, Date date) {
		this.Action=Action;
		this.Amount=Amount;
		this.date=date;
	}

	public Transaction(String Action, Date date) {
		this.Action=Action;
		this.date=date;
	}

	public String getAction(){return Action;}
	public Date getDate(){return date;}
	public int getAmount(){return Amount;}




}
