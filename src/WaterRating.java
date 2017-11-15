import java.sql.Date;

public class WaterRating {

	private int waterbodyID;
	private int numRating;
	private int avgRating; 
	private Date lastUpdated;

	public WaterRating() {
		waterbodyID = 200;
		numRating = 0;
		avgRating = 0;
		lastUpdated = new Date(0);
	}
	
	public WaterRating(int id, int num, int avg, Date date) {
		waterbodyID = id;
		numRating = num;
		avgRating = avg;
		lastUpdated = date;
	}

	public int getWaterbodyID() {
		return waterbodyID;
	}

	public void setWaterbodyID(int waterbodyID) {
		this.waterbodyID = waterbodyID;
	}

	public int getNumRating() {
		return numRating;
	}

	public void setNumRating(int numRating) {
		this.numRating = numRating;
	}

	public int getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(int avgRating) {
		this.avgRating = avgRating;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
