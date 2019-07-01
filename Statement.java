package assignment1_DS;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public interface Statement extends Serializable {

public int getAccountNum();  // returns account number associated with this statement

public Date getStartDate(); // returns start Date of Statement

public Date getEndDate(); // returns end Date of Statement

public String getAccountName(); // returns name of account holder

public ArrayList<Transaction> getTransations(); // returns list of Transaction objects that encapsulate details about each transaction  

}
