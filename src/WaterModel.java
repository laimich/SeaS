import java.sql.*;
import java.sql.DriverManager;

public class WaterModel {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/seas?autoReconnect=true&useSSL=false";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "1234";

	private User currentUser;
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
	
	public String[] getUserInfo(int inputUserID){
		String[] info = new String[3];
		ResultSet userRs;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			String sql = "SELECT userName, credentials FROM User WHERE userID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inputUserID);
			userRs = pstmt.executeQuery();
			info[0] = userRs.getString("userName");
			info[1] = userRs.getInt("credentials") + "";
			
			cs = conn.prepareCall("{CALL vewNumReviews(?, ?)}");
			cs.setInt(1, inputUserID);
			cs.registerOutParameter(2, Types.INTEGER);
			if(pstmt.execute()){
				info[2] = cs.getInt("numViews") + "";
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
			System.out.println(rs.getFetchSize());
			//if(!rs.next()) System.out.println(username + password + false);
			if(rs.next()) {
				System.out.println(true);
				int ID = rs.getInt("userID");
				String name = rs.getString("userName");
				String pass = rs.getString("pass");
				int cred = rs.getInt("credentials");
				String title = rs.getString("title");
				currentUser = new User(ID, name, pass, cred, title);
				return true;
			}else{
				System.out.println(username + password + false);
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
	
	
	public String getCurrentUserTitle() {
		return currentUser.getTitle();
	}

}
