package annuaire.Annuaire.business.employees;

import annuaire.Annuaire.controller.employees.model.Employees;
import annuaire.Annuaire.controller.employees.model.NewEmployees;
import annuaire.Annuaire.dao.employees.EmployeesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeesBusiness {
    private final EmployeesDAO employeesDAO;

    @Autowired
    public EmployeesBusiness(EmployeesDAO employeesDAO){
        this.employeesDAO = employeesDAO;
    }

    public Employees createEmployeesService (NewEmployees employees) {
        return employeesDAO.create(employees);
    }
    public boolean deleteEmployeesService (int id) {
        return employeesDAO.delete(id);
    }
    public Employees updateEmployeesService (int id, NewEmployees employees) {
        return employeesDAO.update(id, employees);
    }
    public Employees getOneEmployeesService (int id) {
        return employeesDAO.getOne(id);
    }
    public List<Employees> getAllEmployeesService() {
        return employeesDAO.getAll();
    }
    public List<Employees> searchEmployeesByNameService(String searchEmployees) {
        return employeesDAO.searchEmployeesByName(searchEmployees);
    }


}
