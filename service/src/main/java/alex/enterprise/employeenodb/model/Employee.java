package alex.enterprise.employeenodb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Comparable<Employee> {
    //    @Valid
//    @NotNull(message = "'id' is required")
    private Integer id;
    //    @Valid
//    @NotNull(message = "'name' is required")
    private String name;
    //    @Valid
//    @NotNull(message = "'passportNumber' is required")
    private String passportNumber;
    //    @Valid
//    @NotNull(message = "'education' is required")
    private String education;

    @Override
    public int compareTo(Employee employee) {
        return this.id.compareTo(employee.id);
    }
}
