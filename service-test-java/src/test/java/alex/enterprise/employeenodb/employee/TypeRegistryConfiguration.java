package alex.enterprise.employeenodb.employee;

import alex.enterprise.employeenodb.model.Employee;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

import java.util.Locale;
import java.util.Map;

import static java.util.Locale.ENGLISH;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {

        typeRegistry.defineDataTableType(new DataTableType(
                Employee.class,
                (Map<String, String> row) -> new Employee(
                        Integer.valueOf(row.get("id")),
                        row.get("name"),
                        row.get("passportNumber"),
                        row.get("education")
                )
        ));
    }

}
