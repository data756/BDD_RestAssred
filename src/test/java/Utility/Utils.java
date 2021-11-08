package Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import bdd.studentenrollment.beans.Student;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import resources.Constants;


public class Utils {

	public static PrintStream getLogger() throws FileNotFoundException {
		File actualFile = getLoggerFileName();
		PrintStream log = new PrintStream(new FileOutputStream(actualFile));
		return log;
	}

	public static File getLoggerFileName() {
		String pathLog = Constants.USER_DIRECTORY + Constants.LOG_DIRECTORY;
		new File(pathLog).mkdirs();
		File dir = new File(pathLog);
		File logFile = new File(dir, getArtifactName("StudentEnrollment", "-", ".log"));
		return logFile;
	}

	public static String getArtifactName(String mainTitle, String seprator, String fileFormat) {
		StringBuilder loggerName = new StringBuilder().append(mainTitle).append(seprator).append(getCurrentDateTime())
				.append(fileFormat);
		return loggerName.toString();
	}

	public static String getCurrentDateTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String date = formatter.format(currentDate.getTime()).replace("/", "-");
		date = date.replace(":", "-");
		System.out.println("Date and Time :: " + date);
		return date;
	}

	public static String getValueForKey(Response response, String key) {
		String responseString = response.asString();
		JsonPath js = new JsonPath(responseString);
		return js.get(key).toString();
	}
	
	public static String formatJSON(String uglyJsonString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJsonString);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}

	public static String objectToJsonString(Student student) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = formatJSON(ow.writeValueAsString(student));
		return json;
	}

	public static boolean compareTwoStudentsAsString(Student expected, Student actual) {
		boolean isEqual=true;
		if (!(expected.toString()).equalsIgnoreCase(expected.toString())) {
			isEqual=false;
		}
		return isEqual;
	}
	
	public static List<Student> jsonToObjectList(Response response) {
		ObjectMapper map = new ObjectMapper();
		List<Student> studentArray = new ArrayList<Student>();
		try {
			studentArray = map.readValue(response.asString(), new TypeReference<List<Student>>() {});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentArray;
	}
	
	public static boolean validateResponseWithEachDatabaseRecord(Response response, List<Student> expectedStudentList) {
		List<Student> actualStudentList= new ArrayList<Student>();
		boolean flag=true;
		actualStudentList=jsonToObjectList(response);
		for(Student a:actualStudentList) {
			flag=expectedStudentList.stream().anyMatch(e -> e.getId()==a.getId());
			if(flag) {
				continue;
			}
			else {
				flag=false;
				break;
			}
		}
		return flag;
	}
	
	

}
