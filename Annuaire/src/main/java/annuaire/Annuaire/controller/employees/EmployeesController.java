package annuaire.Annuaire.controller.employees;

import annuaire.Annuaire.business.employees.EmployeesBusiness;
import annuaire.Annuaire.controller.employees.model.Employees;
import annuaire.Annuaire.controller.employees.model.NewEmployees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class EmployeesController {
    private final String version = "/api/v1";
    private final EmployeesBusiness employeesBusiness;

    @Autowired
    public EmployeesController(EmployeesBusiness employeesBusiness) {
        this.employeesBusiness = employeesBusiness;
    }

    @PostMapping(version + "/employees")
    public Employees createEmployees(@RequestBody NewEmployees employees) {
        return employeesBusiness.createEmployeesService(employees);
    }
    @DeleteMapping(version + "/employees/{id}")
    public boolean deleteEmployee(@PathVariable int id) {
        return employeesBusiness.deleteEmployeesService(id);
    }
    @PutMapping(version + "/employees/{id}")
    public Employees updateEmployee(@PathVariable int id, @RequestBody NewEmployees employees) {
        return employeesBusiness.updateEmployeesService(id, employees);
    }
    @GetMapping(version + "/employees/{id}")
    public Employees getOneEmployee(@PathVariable int id) {
        return employeesBusiness.getOneEmployeesService(id);
    }
    @GetMapping(version + "/employees")
    public List<Employees> getAllEmployee() {
        return employeesBusiness.getAllEmployeesService();
    }
    @GetMapping(version + "/employees/searchEmployee")
    public List<Employees> searchEmployeeByName(@RequestParam String searchEmployees) {
        return employeesBusiness.searchEmployeesByNameService(searchEmployees);
    }
}
