package assignment1_DS;

public class InvalidSession extends Exception {

	public InvalidSession(String Problem) {
		System.out.println("ERROR:"+Problem);
		return;
	}
	public String getErrorMessage(String Problem) {
		return Problem;
	}

}
