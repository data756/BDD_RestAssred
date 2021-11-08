package resources;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import Utility.Utils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CustomRequestResponseBuilder extends Utils {

	public static RequestSpecification reqSpec;
	public static PrintStream log;
	public static ResponseSpecification createdResponseSpec;

	public void setLogger() {
		try {
			if (log == null) {
				log = getLogger();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public RequestSpecification RequestSpecificationBuilder(String httpMethod, String parameter) {
		try {
			if (httpMethod.equalsIgnoreCase("post") || httpMethod.equalsIgnoreCase("delete")) {
				reqSpec = new RequestSpecBuilder().setBaseUri(Constants.BaseURI)
						.addFilter(RequestLoggingFilter.logRequestTo(log))
						.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			}

			else if (httpMethod.equalsIgnoreCase("get")) {
				reqSpec = new RequestSpecBuilder().setBaseUri(Constants.BaseURI)
						.addQueryParam("id", Integer.valueOf(parameter))
						.addFilter(RequestLoggingFilter.logRequestTo(log))
						.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			}
			else if(httpMethod.equalsIgnoreCase("put")) {
				reqSpec = new RequestSpecBuilder().setBaseUri(Constants.BaseURI)
						.addParam("id", Integer.valueOf(parameter))
						.addFilter(RequestLoggingFilter.logRequestTo(log))
						.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqSpec;
	}
	
	public RequestSpecification getFetchStudentsRequestSpec(String httpMethod, String parameter) {
		return new RequestSpecBuilder().setBaseUri(Constants.BaseURI)
				.addQueryParam("classInformation", parameter)
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
	}

	public ResponseSpecification getResponseSpecification(Integer statusCode) {
	
			createdResponseSpec = new ResponseSpecBuilder().expectStatusCode(statusCode).build();
			return createdResponseSpec;
		
	}
}
