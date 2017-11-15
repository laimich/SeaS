
public class User {

	private int userID;
	private String userName;
	private String password;
	private int credentials; //lower = less authority, higher = more authority
	private String type;
	
	public User() {
		userID = 1000;
		userName = "";
		password = "";
		credentials = 0;
		type = "user";
		
	}
	
	public User(int id, String un, String pw, int cred, String t) {
		userID = id;
		userName = un;
		password = pw;
		credentials = cred;
		type = t;
	}
	
	public int getID() { 
		return userID; 
	}
	
	public void setID(int id) { 
		userID = id; 
	}
	
	public String getName() { 
		return userName; 
	}
	
	public boolean checkPassword(String check) {
		if(check.equals(password)) return true;
		return false;
	}
	
	public void setPassword(String pw) { 
		password = pw; 
	}
	
	public int getCredentials() {
		return credentials;
	}
	
	public void setCredentials(int cred) {
		credentials = cred;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(User admin, String t) {
		if(admin.getType().equals("admin")) type = t;
		else type = "user";
	}
}
