package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bdd.studentenrollment.beans.Student;

public class DBUtility {
	
	public static DBConnection dbConnection= new DBConnection();
		
	public static Connection setUpMySqlDBConnection() throws ClassNotFoundException {
		return dbConnection.createMySqlConnection();
	}
	
	public static Statement setUpMySqlStatement() throws SQLException, ClassNotFoundException {
		return dbConnection.createStatement();
	}
	
	public static ArrayList<Student> resultSetToList(ResultSet rs) throws  Throwable{
		ArrayList<Student> studentList= new ArrayList<Student>();
		while(rs.next()) {
			Student student = new Student();
			student.setId(Integer.valueOf(rs.getString(1)));
			student.setFirstName(rs.getString(2));
			student.setLastName(rs.getString(3));
			student.setClassInfo(rs.getString(4));
			student.setNationality(rs.getString(5));
			studentList.add(student);
		}
		return studentList;
	}
	
	public static Student extractSingleStudent(ArrayList<Student> list) {	
		return list.get(0);
	}
	
	public static Student extractStudent(ResultSet rs) throws Throwable {
		ArrayList<Student> studentList=resultSetToList(rs);
		return extractSingleStudent(studentList);
		
	}
	
	
}
