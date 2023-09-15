package alex.enterprise.employeenodb.spring

import alex.enterprise.employeenodb.EmployeeRestServiceNodbApplication
import alex.enterprise.employeenodb.util.session.Session
import org.assertj.core.api.SoftAssertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration

import javax.annotation.PostConstruct

@SpringBootTest(classes = EmployeeRestServiceNodbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class SpringIntegrationTest {

    protected String baseUrl;
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected Session session;
    @LocalServerPort
    private int port;
    @Value('${employee.service.host}')
    private String employeeServiceHost;

    @Bean
    protected SoftAssertions getSoftAssertions() {
        return new SoftAssertions();
    }

    @PostConstruct
    private void init() {
        baseUrl = String.format("http://%s:%s", employeeServiceHost, port);
    }
}
