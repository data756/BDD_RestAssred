package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	public static Connection con=null;
	public static Statement statment=null;
	private static final String URL="jdbc:mysql://localhost:3306/Student";
	private static final String USERNAME="root";
	private static final String PASSWORD="admin";
	
	public Connection createMySqlConnection() throws ClassNotFoundException {
	
		if(con==null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				
			}
			catch(Exception e) {
				System.out.println("Error occurs while creating connection to mysql database" + e.getMessage());
			}
		}
		return con;
	}
	
	public Statement createStatement() throws SQLException, ClassNotFoundException {
		if(statment==null) {
			statment=createMySqlConnection().createStatement();
		}
		return statment;
	}
	
}
