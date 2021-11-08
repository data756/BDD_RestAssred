package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bdd.studentenrollment.beans.Student;

public class StudentService extends DBUtility {

	/**
	 * Class to get Student information
	 *
	 * @return Student information
	 * @throws Throwable
	 */

	public static Statement statment = null;

	public static void setDBtimeZone() throws ClassNotFoundException, SQLException {
		statment = setUpMySqlStatement();
		statment.executeQuery("SET GLOBAL time_zone = '+3:00';");
	}

	public Student getStudentInformationById(Integer id) throws ClassNotFoundException, SQLException {
		setDBtimeZone();
		Student student = null;
		try {
			String SQL = "select * from StudentEnrollment where id=" + id + ";";
			if (statment == null) {
				statment = setUpMySqlStatement();
			}
			ResultSet queryResult = statment.executeQuery(SQL);
			student = extractStudent(queryResult);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}

	public List<Student>  getStudentInformationByClass(String classInformation){
		List<Student> studentList= new ArrayList<Student>();
		
		String sql="select * from StudentEnrollment where classInfo='"+classInformation+"';";
		try {
			if(statment==null) {
				statment = setUpMySqlStatement();

				}
			ResultSet rs=statment.executeQuery(sql);
			while(rs.next()) {
				Student student= new Student();
				student.setId(Integer.valueOf(rs.getString(1)));
				student.setFirstName(rs.getString(2));
				student.setLastName(rs.getString(3));
				student.setClassInfo(rs.getString(4));
				student.setNationality(rs.getString(5));
				studentList.add(student);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
}
