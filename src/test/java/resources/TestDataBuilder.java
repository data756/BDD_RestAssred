package resources;

import bdd.studentenrollment.beans.Student;

public class TestDataBuilder {

	public  Student createPayload(String id, String firstName, String lastName, String classInfo, String nationality) {
		Student student= new Student();
		student.setId(Integer.valueOf(id));
		student.setClassInfo(classInfo);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setNationality(nationality);
		return student;
	}
	
	public String getDeleteStudentPayload(String id) {
		return "{ \r\n"
				+ "\r\n"
				+ "    \"id\":\""+id+"\"\r\n"
				+ "\r\n"
				+ "} ";
	}
	
}
