package stepdefinition;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import bdd.studentenrollment.beans.Student;
import db.StudentService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.ApiResources;
import resources.CustomRequestResponseBuilder;
import resources.TestDataBuilder;

public class stepDefinitions extends CustomRequestResponseBuilder {

	static Student student;
	static Student expectedStudent;
	static TestDataBuilder testDataBuilder;
	static List<Student> expectedStudentList;
	Response response;
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	static String currentServiceName;
	static StudentService database = new StudentService();

	@Given("student information : {string},{string},{string},{string}, {string}")
	public void student_information(String id, String firstName, String lastName, String classInfo,
			String Nationality) {
		testDataBuilder = new TestDataBuilder();
		student = testDataBuilder.createPayload(id, firstName, lastName, classInfo, Nationality);
	}

	@When("user calls {string} api with {string} http request")
	public void user_calls_api_with_http_request(String serviceName, String httpMethodType) {
		currentServiceName = serviceName;
		ApiResources resources = ApiResources.valueOf(serviceName);
		String resourceInfo = resources.getResource();
		setLogger();
		responseSpecification = getResponseSpecification(200);
		if (httpMethodType.equalsIgnoreCase("post")) {
			responseSpecification = getResponseSpecification(201);
			response = given().spec(RequestSpecificationBuilder("post", "null")).body(student).when().post(resourceInfo)
					.then().spec(responseSpecification).extract().response();
		} else if (httpMethodType.equalsIgnoreCase("get") && (!serviceName.equalsIgnoreCase("fetchStudents"))) {

			response = given().spec(RequestSpecificationBuilder("get", String.valueOf(expectedStudent.getId()))).when()
					.get(resourceInfo).then().spec(responseSpecification).extract().response();
		} else if (httpMethodType.equalsIgnoreCase("put")) {

			response = given().spec(RequestSpecificationBuilder("put", String.valueOf(expectedStudent.getId())))
					.body(student).when().put(resourceInfo + "/" + student.getId()).then().spec(responseSpecification)
					.extract().response();
		} else if (httpMethodType.equalsIgnoreCase("delete")) {
			response = requestSpecification.when().delete(resourceInfo).then().spec(responseSpecification).extract()
					.response();
		}

		else if (serviceName.equalsIgnoreCase("fetchStudents")) {
			response = requestSpecification.get(resourceInfo).then().spec(responseSpecification).extract().response();
		}
	}

	@Then("resonse status should be {int}")
	public void resonse_status_should_be(Integer status) {
		assertEquals(status.intValue(), response.getStatusCode());
	}

	@And("verify the response content with fetched backend database record")
	public void verify_the_response_content_with_fetched_backend_database_record()
			throws JsonProcessingException, ClassNotFoundException, SQLException {
		if (currentServiceName.equalsIgnoreCase("addstudent") || currentServiceName.equalsIgnoreCase("updatestudent")) {
			expectedStudent = database.getStudentInformationById(student.getId());
			assertTrue(compareTwoStudentsAsString(student, expectedStudent));
		} else if (currentServiceName.equalsIgnoreCase("fetchstudent")) {
			String expectedStudentJson = objectToJsonString(expectedStudent);
			assertEquals(expectedStudentJson, formatJSON(response.asPrettyString()));
		} else if (currentServiceName.equalsIgnoreCase("deletestudent")) {
			expectedStudent = database.getStudentInformationById(expectedStudent.getId());
			assertTrue(expectedStudent == null);
		}
		
		else if(currentServiceName.equalsIgnoreCase("fetchStudents")) {
			assertTrue(validateResponseWithEachDatabaseRecord(response,expectedStudentList));
		}
	}

	@Given("you have enrolled student information : {int}")
	public void you_have_enrolled_student_information(Integer studentId) throws ClassNotFoundException, SQLException {
		if (studentId != student.getId()) {
			expectedStudent = database.getStudentInformationById(studentId);
		}
	}

	@Given("you have enrolled student information in payload : {int}")
	public void you_have_enrolled_student_information_in_payload(Integer studentId)
			throws ClassNotFoundException, SQLException {
		requestSpecification = given().spec(RequestSpecificationBuilder("delete", ""))
				.body(testDataBuilder.getDeleteStudentPayload(String.valueOf(studentId)));
		expectedStudent = database.getStudentInformationById(studentId);
	}

	@Given("you have student class information : {string}")
	public void you_have_student_class_information(String classInfo) {
		requestSpecification = given().spec(getFetchStudentsRequestSpec("get", classInfo)).when();
		expectedStudentList=database.getStudentInformationByClass(classInfo);

	}

}