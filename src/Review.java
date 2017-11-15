import java.sql.Date;

public class Review {

	private int userID;
	private int waterbodyID;
	private Date reviewDate;
	private int rating;
	
	public Review() {
		userID = 1000;
		waterbodyID = 200;
		reviewDate = new Date(0);
		rating = 0;
	}
	
	public Review(int id, int id2, Date date, int rate) {
		userID = id;
		waterbodyID = id2;
		reviewDate = date;
		rating = rate;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getWaterbodyID() {
		return waterbodyID;
	}

	public void setWaterbodyID(int waterbodyID) {
		this.waterbodyID = waterbodyID;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
