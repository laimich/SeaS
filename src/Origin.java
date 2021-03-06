
public class Origin {

	private int waterID;
	private String waterName;
	private String location;
	
	public Origin() {
		waterID = 100;
		waterName = "";
		location = "";
	}
	
	public Origin(int id, String name, String loc) {
		waterID = id;
		waterName = name;
		location = loc;
	}
	
	public int getID() {
		return waterID;
	}
	
	public void setID(int id) {
		waterID = id;
	}
	
	public String getName() {
		return waterName;
	}
	
	public void setName(String name) {
		waterName = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String loc) {
		location = loc;
	}
}
