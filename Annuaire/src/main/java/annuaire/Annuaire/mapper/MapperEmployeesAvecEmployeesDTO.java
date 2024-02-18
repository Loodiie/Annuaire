package annuaire.Annuaire.mapper;

import annuaire.Annuaire.controller.employees.model.Employees;
import annuaire.Annuaire.controller.employees.model.NewEmployees;
import annuaire.Annuaire.dao.employees.model.EmployeesDTO;

public class MapperEmployeesAvecEmployeesDTO {

    public EmployeesDTO EmployeesToDTO (Employees employees) {
        EmployeesDTO dto = new EmployeesDTO();
        dto.setIdEmployee(employees.getIdEmployee());
        dto.setNomEmployee(employees.getNomEmployee());
        dto.setPrenomEmployee(employees.getNomEmployee());
        dto.setIdService(employees.getIdService());
        dto.setPosteEmployee(employees.getPosteEmployee());
        dto.setFixeEmployee(employees.getFixeEmployee());
        dto.setMailEmployee(employees.getMailEmployee());
        dto.setDateNaissance(employees.getDateNaissance());
        dto.setDateEmbauche(employees.getDateEmbauche());
        dto.setAdmin(employees.getAdmin());

        return dto;
    }

    public EmployeesDTO NewEmployeesToDTO (NewEmployees employees) {
        EmployeesDTO dto = new EmployeesDTO();
        dto.setNomEmployee(employees.getNomEmployee());
        dto.setPrenomEmployee(employees.getPrenomEmployee());
        dto.setIdService(employees.getIdService());
        dto.setPosteEmployee(employees.getPosteEmployee());
        dto.setFixeEmployee(employees.getFixeEmployee());
        dto.setMailEmployee(employees.getMailEmployee());
        dto.setDateNaissance(employees.getDateNaissance());
        dto.setDateEmbauche(employees.getDateEmbauche());
        dto.setAdmin(employees.getAdmin());

        return dto;
    }

    public Employees DTOToEmployees (EmployeesDTO dto) {
        Employees employees = new Employees();
        employees.setIdEmployee(dto.getIdEmployee());
        employees.setNomEmployee(dto.getNomEmployee());
        employees.setPrenomEmployee(dto.getPrenomEmployee());
        employees.setIdService(dto.getIdService());
        employees.setPosteEmployee(dto.getPosteEmployee());
        employees.setFixeEmployee(dto.getFixeEmployee());
        employees.setMailEmployee(dto.getMailEmployee());
        employees.setDateNaissance(dto.getDateNaissance());
        employees.setDateEmbauche(dto.getDateEmbauche());
        employees.setAdmin(dto.getAdmin());

        return employees;
    }

}
