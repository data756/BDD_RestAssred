package bdd.studentenrollment.options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		
		features="src/test/java/features",
		glue= {"stepdefinition"},
		monochrome = true,
		plugin={"pretty","json:target/jsonReports/executionReport.json"}				
)
public class TestRunner {

}
