package assignment1_DS;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Date;

public interface BankInterface extends Remote {

public long login(String username, String password, boolean FirstTime) throws RemoteException, InvalidLogin;

public void deposit(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;

public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;

public int inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession;

public ArrayList<String> getStatement(int account,Date from, Date to, long sessionID) throws RemoteException, InvalidSession;

public long logoff(String username, String password) throws RemoteException, InvalidLogin;

public int getBankAccountNumber(String username) throws RemoteException, InvalidLogin;
}