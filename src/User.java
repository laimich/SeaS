
public class User {

	private int userID;
	private String userName;
	private String pass;
	private int credentials; //lower = less authority, higher = more authority
	private String title;
	
	public User() {
		userID = 1000;
		userName = "";
		pass = "";
		credentials = 0;
		title = "user";
		
	}
	
	public User(int id, String un, String pw, int cred, String t) {
		userID = id;
		userName = un;
		pass = pw;
		credentials = cred;
		title = t;
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
		if(check.equals(pass)) return true;
		return false;
	}
	
	public void setPassword(String pw) { 
		pass = pw; 
	}
	
	public int getCredentials() {
		return credentials;
	}
	
	public void setCredentials(int cred) {
		credentials = cred;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(User admin, String t) {
		if(admin.getTitle().equals("admin")) title = t;
		else title = "user";
	}
}
