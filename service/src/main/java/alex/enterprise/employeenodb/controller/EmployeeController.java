package alex.enterprise.employeenodb.controller;

import alex.enterprise.employeenodb.model.Employee;
import alex.enterprise.employeenodb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public String add(@Validated @RequestBody Employee employee) {
        employeeService.add(employee);
        return String.format("Employee with id = %s has been added!", employee.getId());
    }

    @PostMapping("/list")
    public List<Employee> addList(@Validated @RequestBody List<Employee> employees) {
        employeeService.addList(employees);
        return employeeService.getAll();
    }

    @PutMapping
    public String update(@Validated @RequestBody Employee employee) {
        employeeService.update(employee);
        return String.format("Employee with id = %s has been updated!", employee.getId());
    }

    @PatchMapping
    public String updatePatch(@Validated @RequestBody Employee employee) {
        employeeService.updatePatch(employee);
        return String.format("Employee with id = %s has been partially updated!", employee.getId());
    }

    @DeleteMapping("/{id}")
    public String removeEmployeeById(@PathVariable Integer id) {
        employeeService.deleteById(id);
        return String.format("Employee with id = %s has been successfully deleted!", id);
    }

    @DeleteMapping
    public String deleteAll() {
        employeeService.clear();
        return "All employees have been successfully deleted!";
    }
}
