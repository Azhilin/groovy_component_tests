package alex.enterprise.employeenodb.employee

import alex.enterprise.employeenodb.exception.CustomRuntimeException
import alex.enterprise.employeenodb.model.Employee
import alex.enterprise.employeenodb.spring.SpringIntegrationTest
import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import io.cucumber.datatable.DataTable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

import java.util.stream.Collectors

import static alex.enterprise.employeenodb.util.session.SessionKey.*

class EmployeeDefStep extends SpringIntegrationTest {

    @After
    void cleanUp() {

        restTemplate.delete(String.format("%s/employee", baseUrl))
        getSoftAssertions().assertAll()
        session.clear()
    }

    @Given("employees added to Employee rest service repository:")
    void addListOfEmployees(DataTable dataTable) {
        List<Employee> employees = []

        dataTable.asMaps().each {
            Employee employee = new Employee(
                    Integer.valueOf(it.id),
                    it.name,
                    it.passportNumber,
                    it.education
            )
            employees.add(employee)
        }

        restTemplate.put(String.format("%s/employee/list", baseUrl), employees)
        List<Employee> expectedList = employees.stream().sorted().collect(Collectors.toList())
        session.put(EXPECTED_LIST, expectedList)
    }


    @When("we send {string} request to the {string} endpoint")
    void weSendGETRequestToTheEmployeeEndpoint(String methodName, String endpoint) {
        ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(
                String.format("%s/%s", baseUrl, endpoint),
                HttpMethod.resolve(methodName),
                null,
                new ParameterizedTypeReference<List<Employee>>() {
                }
        )

        List<Employee> actualList = Objects.requireNonNull(responseEntity.getBody()).stream().sorted().collect(Collectors.toList())
        session.put(ACTUAL_LIST, actualList)
    }

    @Then("retrieved data is equal to added data")
    void retrievedDataIsEqualToAddedData() {
        getSoftAssertions()
                .assertThat(session.get(ACTUAL_LIST, Object.class))
                .isEqualTo(session.get(EXPECTED_LIST, Object.class))
    }

    @When("we send {string} request to the {string} endpoint with {int} id")
    void weSendGETRequestToTheEmployeeEndpointWithId(String methodName, String endpoint, int id) {
        ResponseEntity<Employee> responseEntity = restTemplate.exchange(
                String.format("%s/%s/%s", baseUrl, endpoint, id),
                HttpMethod.resolve(methodName),
                null,
                Employee.class
        )

        Employee actual = Objects.requireNonNull(responseEntity.getBody())

        session.put(ACTUAL_RESULT, actual)

        List<Employee> expectedList = (List<Employee>) session.get(EXPECTED_LIST, Object.class)

        Employee expected = expectedList.stream().filter(employee -> employee.getId() == id).findFirst()
                .orElseThrow(() -> new CustomRuntimeException(String.format("Expected employee not found with id = %s", id)))

        session.put(EXPECTED_RESULT, expected)
    }

    @Then("retrieved data is equal to added data for specified id")
    void retrievedDataIsEqualToAddedDataForSpecifiedId() {
        getSoftAssertions()
                .assertThat(session.get(ACTUAL_RESULT, Employee.class))
                .isEqualTo(session.get(EXPECTED_RESULT, Employee.class))
    }
}
