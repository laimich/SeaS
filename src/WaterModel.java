import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WaterModel {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/seas?autoReconnect=true&useSSL=false";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "1234";//"newpass";

	private User currentUser;
	private int searchID;
	private String searchType;
	private String searchName;

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private CallableStatement cs;

	public WaterModel() {
		currentUser = null;
		conn = null;
		stmt = null;
		pstmt = null;
		cs = null;
	}

	public void createAccount(String username, String password) {
		conn = null;
		cs = null;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL createAccount(?, ?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			cs.executeUpdate();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}

	public boolean isAccountAvailable(String checkName) {
		ResultSet rs = null;
		String sql = "";
		conn = null;
		pstmt = null;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			sql = "SELECT * FROM User WHERE userName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkName);
			rs = pstmt.executeQuery();
			//if no existing accounts with the same name, return true
			if(!rs.next()) return true;
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return false;
	}

	public String[] getUserHomeInfo(){
		//0 = userName, 1 = credentials, 2 = num reviews
		String[] info = new String[3];
		ResultSet userRs;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//set current user's userName
			info[0] = currentUser.getName();
			//set current user's credentials
			info[1] = currentUser.getCredentials() + "";

			//execute query for current user's num reviews given
			cs = conn.prepareCall("{CALL viewNumReviews(?)}");
			cs.setInt(1, currentUser.getID());
			if(cs.execute()){
				userRs = cs.getResultSet();
				userRs.next();
				info[2] = userRs.getInt(1) + "";
			} else {
				info[2] = "0";
			}
		} catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return info;
	}

	public boolean canLogin(String username, String password) {
		ResultSet rs = null;
		conn = null;
		cs = null;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL userLogin(?, ?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			rs = cs.executeQuery();
			if(rs.next()) {
				int ID = rs.getInt("userID");
				String name = rs.getString("userName");
				String pass = rs.getString("pass");
				int cred = rs.getInt("credentials");
				String title = rs.getString("title");
				currentUser = new User(ID, name, pass, cred, title);
				return true;
			}else{
				//...
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try

		return false;
	}

	public int getSearchID(String name) {
		ResultSet rs = null;
		searchName = name.trim();
		if (searchName.isEmpty()) {
			return 0;
		}else {
		
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL searchWaterBodyOrLocation(?)}");
			cs.setString(1, name);
			if(cs.execute()) {
				rs = cs.getResultSet();
				rs.next();
				//set search ID
				searchID = rs.getInt(1);
				//set search type
				searchType = rs.getString(2);
				return searchID;
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		}
		return 0;
	}

	public TreeMap<String, String> getWaterbodySearch() {
		ResultSet rs = null;
		TreeMap<String, String> searchResults = new TreeMap<String, String>();
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL viewInfo(?)}");
			cs.setInt(1, searchID);
			if(cs.execute()) {
				rs = cs.getResultSet();
				rs.next();

				//save all search info into treemap
				searchResults.put("location", rs.getString(1));
				searchResults.put("waterName", rs.getString(2));
				searchResults.put("waterbodyName", rs.getString(3));
				searchResults.put("avgRating", rs.getInt(4) + "");
				String num = rs.getInt(5) + "";
				if(num.equals(null)) num = "No reviews";
				searchResults.put("numRating", num);
				return searchResults;
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try

		return null;
	}

	public String getLocationAvgRating() {
		ResultSet rs = null;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL avgRatingLocation(?)}");
			cs.setString(1, searchName);
			if(cs.execute()) {
				rs = cs.getResultSet();
				rs.next();
				//get and return the avg of the location
				String avg = rs.getInt(1) + "";
				if(avg.equals(null)) avg = "0";
				return avg;
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try

		return "0";
	}

	public ArrayList<String[]> getLocationWaterbodies() {
		ArrayList<String[]> info = new ArrayList<String[]>();
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL viewWaterbodiesFromLocation(?)}");
			cs.setInt(1, searchID);
			if(cs.execute()) {
				ResultSet rs = cs.getResultSet();
				while(rs.next()) {
					String[] entry = new String[2];
					entry[0] = rs.getString(1); //origin name for waterbody
					entry[1] = rs.getString(2); //waterbody name
					info.add(entry);
				}
			}
			return info;
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try

		return null;
	}
	
	public boolean doesOriginExist(String originName) {
		ResultSet rs = null;
		String sql = "";
		conn = null;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			sql = "SELECT * FROM Origin WHERE waterName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, originName);
			rs = pstmt.executeQuery();
			//if origin doesn't exist, return false
			if(!rs.next()) return false;
			return true;
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return false;
	}
	
	public void addWaterbody(String waterbodyName, String originName, int minCred) {
		ResultSet rs = null;
		conn = null;
		cs = null;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL addWaterbody(?, ?, ?)}");
			cs.setString(1, waterbodyName);
			cs.setString(2, originName);
			cs.setInt(3, minCred);
			cs.execute();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}

	
	public void updateArchive(Date currentDate) {
		//call archive procedure @ every launch
		//cutoff date is 3 years prior current date
		int cutOffYear = currentDate.getYear() - 3;
		Date cutOff = currentDate;
		cutOff.setYear(cutOffYear);
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			cs = conn.prepareCall("{CALL archiveReviews(?)}");
			cs.setDate(1, cutOff);
			cs.execute();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}
	

	public String getCurrentUserTitle() {
		return currentUser.getTitle();
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchName() {
		return searchName;
	}
	
	

	public boolean checkCredandInputReview(int reviewNum) throws SQLException{
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			CallableStatement cs = conn.prepareCall("{CALL checkCredentials(?, ?)}");
			cs.setInt(1, currentUser.getID());
			cs.setInt(2, searchID);
			
			CallableStatement cs2 = conn.prepareCall("{CALL addRating(?,?,?,?)}");
			Date currentDate = new Date(System.currentTimeMillis());
			cs2.setInt(1, currentUser.getID());
			cs2.setInt(2, searchID);
			cs2.setInt(3, reviewNum);
			cs2.setDate(4, currentDate);
			ResultSet rs = cs.executeQuery();
			if(rs.next()) {
				cs2.execute();
				return true;
			}
			return false;
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		
		return false;
	}
	

	public ArrayList<String[]> getAllReviews() throws SQLException{
		ArrayList<String[]> info = new ArrayList<String[]>();
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			CallableStatement cs = conn.prepareCall("{CALL adminViewReview()}");		
			if(cs.execute()) {
				ResultSet rs = cs.getResultSet();
				int i = 1;
				while(rs.next()) {
					String[] entry = new String[6];
					entry[0] = i + "";
					entry[1] = rs.getInt(1) + ""; //userID of review
					entry[2] = rs.getString(2); //userName of review
					entry[3] = rs.getString(3); //waterbody name of review
					entry[4] = rs.getDate(4) + ""; //date of review
					entry[5] = rs.getInt(5) + ""; //rating of review
					info.add(entry);
					i++;
				}
				return info;
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return null;
	}
	
	
	
	
	//For Administration water body search
	public ArrayList<String[]> getAdminWaterbodySearch(String waterbodyInput) throws SQLException{
		ArrayList<String[]> info = new ArrayList<String[]>();
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			CallableStatement cs = conn.prepareCall("{CALL viewAllRatings(?)}");
			cs.setString(1, waterbodyInput);
			
			if(cs.execute()) {
				ResultSet rs = cs.getResultSet();
				int i = 1;
				while(rs.next()) {
					String[] entry = new String[4];
					entry[0] = i + "";
					entry[1] = rs.getInt(1) + ""; //userID of review
					entry[2] = rs.getInt(2) + ""; //waterbody rating
					entry[3] = rs.getDate(3) + ""; //waterbody reviewDate 
					info.add(entry);
					++i;
				}
				return info;
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return null;
	}
	
	
	public void checkDeleteReview() throws SQLException{
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			//String searchName;
			//Date date;
			//int c;
			
			
			CallableStatement cs = conn.prepareCall("{CALL checkCredentials(?,?,?,?)}");	
			//cs.setString(1, searchName);
			//cs.setInt(2, currentUser.getID());
			//cs.setDate(3, date);
			//cs.setInt(4, c);
			cs.execute();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}
	
	// get waterbody's credentials
	public int getWaterCredential() {
		int credential = -1;
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			String sql = "select minCredentials from waterbody where waterbodyID = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, searchID);
			ResultSet rs = pstmt.executeQuery();
			//if no existing accounts with the same name, return true
			if(rs.next()) {
				credential = rs.getInt("minCredentials");
			}
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return credential;
	}
	
	// update waterbody credential
	public void updateWaterbodyCredential(int credential) {
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			CallableStatement cs = conn.prepareCall("{CALL updateWaterbody(?,?)}");	
			cs.setInt(1, credential);
			cs.setString(2, searchName);
			cs.execute();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}
	
	// update waterbody credential
	public void updateUserCredential(int credential) {
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
			CallableStatement cs = conn.prepareCall("{CALL updateCredentials(?,?)}");	
			cs.setInt(1, credential);
			cs.setInt(2, currentUser.getID());
			cs.execute();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(stmt!=null) stmt.close(); if(pstmt!=null) pstmt.close(); if(cs!=null) cs.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}
}
