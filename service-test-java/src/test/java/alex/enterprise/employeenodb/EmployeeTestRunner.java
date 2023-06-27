package alex.enterprise.employeenodb;

import alex.enterprise.employeenodb.spring.SpringIntegrationTest;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", tags = "@employee")
public class EmployeeTestRunner extends SpringIntegrationTest {
}
