package annuaire.Annuaire.dao.employees;

import annuaire.Annuaire.controller.employees.model.Employees;
import annuaire.Annuaire.controller.employees.model.NewEmployees;
import annuaire.Annuaire.dao.employees.model.EmployeesDTO;
import annuaire.Annuaire.mapper.MapperEmployeesAvecEmployeesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeesDAO {
    private static final String ID_FIELD = "idEmploye";
    private static final String NOMEMPLOYEE_FIELD = "nomEmployee";
    private static final String PRENOMEMPLOYEE_FIELD = "prenomEmployee";
    private static final String IDSERVICE_FIELD = "idService";
    private static final String POSTEEMPLOYEE_FIELD = "posteEmployee";
    private static final String FIXEEMPLOYEE_FIELD = "fixeEmployee";
    private static final String MAILEMPLOYEE_FIELD = "mailEmployee";
    private static final String DATENAISSANCE_FIELD = "dateNaissance";
    private static final String DATEEMBAUCHE_FIELD = "dateEmbauche";
    private static final String ADMIN_FIELD = "admin";


    private final JdbcTemplate jdbcTemplate;
    private final MapperEmployeesAvecEmployeesDTO mapperEmployeesAvecEmployeesDTO;

    @Autowired
    public EmployeesDAO(DataSource dataSource, MapperEmployeesAvecEmployeesDTO mapperEmployeesAvecEmployeesDTO) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapperEmployeesAvecEmployeesDTO = mapperEmployeesAvecEmployeesDTO;
    }

    private final RowMapper<EmployeesDTO> rowMapper = (rs, rowNum) -> {
        final EmployeesDTO employees = new EmployeesDTO();
        employees.setIdEmploye(rs.getInt(ID_FIELD));
        employees.setNomEmployee(rs.getString(NOMEMPLOYEE_FIELD));
        employees.setPrenomEmployee(rs.getString(PRENOMEMPLOYEE_FIELD));
        employees.setIdService(rs.getInt(IDSERVICE_FIELD));
        employees.setPosteEmployee(rs.getString(POSTEEMPLOYEE_FIELD));
        employees.setFixeEmployee(rs.getString(FIXEEMPLOYEE_FIELD));
        employees.setMailEmployee(rs.getString(MAILEMPLOYEE_FIELD));
        employees.setDateNaissance(rs.getTimestamp(DATENAISSANCE_FIELD));
        employees.setDateEmbauche(rs.getTimestamp(DATEEMBAUCHE_FIELD));
        employees.setAdmin(rs.getBoolean(ADMIN_FIELD));

        return employees;
    };

    public Employees create(NewEmployees employees) {
        EmployeesDTO employees1 = null;
        Employees employees2 = null;

        final String sqlQuery = "INSERT INTO employees (nomEmployee, prenomEmployee, idService, posteEmployee, fixeEmployee, mailEmployee, dateNaissance, dateEmbauche, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int resultCreation = this.jdbcTemplate.update(
                sqlQuery,
                employees.getNomEmployee(),
                employees.getPrenomEmployee(),
                employees.getIdService(),
                employees.getPosteEmployee(),
                employees.getFixeEmployee(),
                employees.getMailEmployee(),
                employees.getDateNaissance(),
                employees.getDateEmbauche(),
                employees.getAdmin()
        );

        if (resultCreation == 1) {
            employees1 = mapperEmployeesAvecEmployeesDTO.NewEmployeesToDTO(employees);
            employees2 = mapperEmployeesAvecEmployeesDTO.DTOToEmployees(employees1);
        }
        return employees2;
    }

    public Employees update(int id, NewEmployees employees) {
        EmployeesDTO employees1 = null;
        Employees employees2 = null;

        final String sqlQuery = "UPDATE employees SET nomEmployee = ?, prenomEmployee = ?, idService = ?, posteEmployee = ?, fixeEmployee = ?, mailEmployee = ?, dateNaissance = ?, dateEmbauche = ?, admin = ? WHERE idEmploye = ?";
        int resultUpdate = this.jdbcTemplate.update(
                sqlQuery,
                employees.getNomEmployee(),
                employees.getPrenomEmployee(),
                employees.getIdService(),
                employees.getPosteEmployee(),
                employees.getFixeEmployee(),
                employees.getMailEmployee(),
                employees.getDateNaissance(),
                employees.getDateEmbauche(),
                employees.getAdmin(),
                id
        );

        if (resultUpdate == 1) {
            employees1 = mapperEmployeesAvecEmployeesDTO.NewEmployeesToDTO(employees);
            employees2 = mapperEmployeesAvecEmployeesDTO.DTOToEmployees(employees1);
        }
        return employees2;
    }
    public boolean delete(int id) {
        final String sqlQuery = "DELETE FROM employees WHERE idEmploye = ?";
        int resultDelete = this.jdbcTemplate.update(
                sqlQuery,
                id
        );

        if (resultDelete == 1) {
            return true;
        } else {
            return false;
        }
    }
    public Employees getOne (int id) {
        Employees employees = null;
        String sqlQuery = "SELECT * FROM employees WHERE idEmploye = ?";
        Object[] param = new Object[]{id};
        List<EmployeesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                param,
                this.rowMapper
        );

        if (dtos != null && dtos.size() == 1) {
            employees = mapperEmployeesAvecEmployeesDTO.DTOToEmployees(dtos.get(0));
        }

        return employees;
    }

    public List<Employees> getAll () {
        List<Employees> listEmployees = null;
        Employees resp = null;

        String sqlQuery = "SELECT * FROM employees";

        List<EmployeesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                this.rowMapper
        );

        if (dtos != null && dtos.size() > 0) {
            listEmployees = new ArrayList<Employees>();

            for (EmployeesDTO dto : dtos) {
                resp = mapperEmployeesAvecEmployeesDTO.DTOToEmployees(dto);
                listEmployees.add(resp);
            }
        }
        return listEmployees;
    }


    public List<Employees> searchEmployeesByName (String searchEmployees) {
        List<Employees> listEmployees = null;

        String sqlQuery = "SELECT * FROM employees WHERE nomEmployee LIKE ? OR prenomEmployee LIKE ?";

        List<EmployeesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                new Object[]{"%" + searchEmployees + "%", "%" + searchEmployees + "%"},
                this.rowMapper
        );


        if (dtos != null && !dtos.isEmpty()) {
            listEmployees = new ArrayList<>();
            for (EmployeesDTO dto : dtos) {
                listEmployees.add(mapperEmployeesAvecEmployeesDTO.DTOToEmployees(dto));
            }
        }
        return listEmployees;
    }



}


