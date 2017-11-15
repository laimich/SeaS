
public class Waterbody {

	private int waterbodyID;
	private String waterbodyName;
	private int waterID;
	private int minCredentials;
	
	public Waterbody() {
		waterbodyID = 200;
		waterbodyName = "";
		waterID = 100;
		minCredentials = 0;
	}
	
	public Waterbody(int id, String name, int id2, int cred) {
		waterbodyID = id;
		waterbodyName = name;
		waterID = id2;
		minCredentials = cred;
	}

	public int getID() {
		return waterbodyID;
	}

	public void setID(int waterbodyID) {
		this.waterbodyID = waterbodyID;
	}

	public String getName() {
		return waterbodyName;
	}

	public void setName(String waterbodyName) {
		this.waterbodyName = waterbodyName;
	}

	public int getWaterID() {
		return waterID;
	}

	public void setWaterID(int waterID) {
		this.waterID = waterID;
	}

	public int getMinCredentials() {
		return minCredentials;
	}

	public void setMinCredentials(int minCredentials) {
		this.minCredentials = minCredentials;
	}
	
	
}
