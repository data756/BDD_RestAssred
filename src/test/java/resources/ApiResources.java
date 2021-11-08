package resources;

public enum ApiResources {

	addstudent("/addstudent"),
	fetchStudent("/fetchStudent"),
	updatestudent("/updatestudent"),
	deletestudent("/deletestudent"),
	fetchStudents("/fetchStudents");
	
	private String resource;
	
	private ApiResources(String resource) {
		
		this.resource=resource;
	}
	
	public String getResource() {
		return resource;
	}
}
