import java.sql.*;
import java.sql.DriverManager;

public class WaterModel {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/seas";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "newpass";

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;

	public WaterModel() {
		conn = null;
		stmt = null;
		pstmt = null;

	}

	public void createAccount(String username, String password) {
		String sql = "";
		try {
			//establish connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//execute query
			sql = "INSERT INTO User (userName, password, type) " +
					"VALUES (?, ?, 'user');";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
		} 
		catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
		catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
		finally{ //finally block used to close resources
			try{ if(pstmt!=null) pstmt.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
	}

	public boolean isAccountAvailable(String checkName) {
		ResultSet rs = null;
		String sql = "";
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
			try{ if(pstmt!=null) pstmt.close(); }
			catch(SQLException se2){} //Nothing we can do
			try{ if(conn!=null) conn.close(); } 
			catch(SQLException se){ se.printStackTrace(); }
			//end finally try
		}//end try
		return false;
	}

}


//try {
////establish connection
//conn = DriverManager.getConnection(DB_URL, USER, PASS);
////execute query
//stmt = conn.createStatement();
//rs = stmt.executeQuery(sql);
//} 
//catch(SQLException se){ se.printStackTrace(); } //Handle errors for JDBC
//catch(Exception e){ e.printStackTrace(); } //Handle errors for Class.forName
//finally{ //finally block used to close resources
//try{ if(stmt!=null) stmt.close(); }
//catch(SQLException se2){} //Nothing we can do
//try{ if(conn!=null) conn.close(); } 
//catch(SQLException se){ se.printStackTrace(); }
////end finally try
//}//end try	
