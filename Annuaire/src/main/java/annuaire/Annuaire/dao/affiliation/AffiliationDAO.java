package annuaire.Annuaire.dao.affiliation;

import annuaire.Annuaire.controller.affiliation.model.Affiliation;
//import annuaire.Annuaire.controller.employees.model.Employees;
import annuaire.Annuaire.dao.adresses.model.AdressesDTO;
import annuaire.Annuaire.dao.affiliation.model.AffiliationDTO;
//import annuaire.Annuaire.dao.employees.model.EmployeesDTO;
import annuaire.Annuaire.dao.services.model.ServicesDTO;
//import annuaire.Annuaire.dao.sites.model.SitesDTO;
import annuaire.Annuaire.mapper.MapperAffiliationAvecAffiliationDTO;
//import annuaire.Annuaire.mapper.MapperEmployeesAvecEmployeesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AffiliationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final MapperAffiliationAvecAffiliationDTO mapperAffiliationAvecAffiliationDTO;

    @Autowired
    public AffiliationDAO(DataSource dataSource, MapperAffiliationAvecAffiliationDTO mapperAffiliationAvecAffiliationDTO) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapperAffiliationAvecAffiliationDTO = mapperAffiliationAvecAffiliationDTO;
    }

    private final RowMapper<AffiliationDTO> rowMapper = (rs, rowNum) -> {
        final AffiliationDTO affiliation = new AffiliationDTO();

//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setIdEmployee(rs.getInt("employee.id_employee"));
//        employeeDTO.setFirstName(rs.getString("employee.first_name"));
//        employeeDTO.setLastName(rs.getString("employee.last_name"));
//        employeeDTO.setJobEmployee(rs.getString("employee.job"));
//        employeeDTO.setServiceEmployee(rs.getInt("employee.service_employee"));
//        employeeDTO.setPhoneEmployee(rs.getString("employee.phone_employee"));
//        employeeDTO.setCellphoneEmployee(rs.getString("employee.cellphone_employee"));
//        employeeDTO.setEmailEmployee(rs.getString("employee.email_employee"));
//        employeeDTO.setBirthDate(rs.getTimestamp("employee.birthdate"));
//        employeeDTO.setHiringDate(rs.getTimestamp("employee.hiring_date"));
//        employeeDTO.setAdminApplication(rs.getBoolean("employee.admin_application"));
//
//        WorksiteDTO worksiteDTO = new WorksiteDTO();
//        worksiteDTO.setIdWorksite(rs.getInt("worksite.id_worksite"));
//        worksiteDTO.setNameWorksite(rs.getString("worksite.name_worksite"));
//        worksiteDTO.setTypeWorksite(rs.getString("worksite.type_worksite"));
//        worksiteDTO.setPhoneWorksite(rs.getString("worksite.phone_worksite"));
//        worksiteDTO.setEmailWorksite(rs.getString("worksite.email_worksite"));
//        worksiteDTO.setIdAddress(rs.getInt("worksite.address_worksite"));
//
        ServicesDTO servicesDTO = new ServicesDTO();
        servicesDTO.setIdService(rs.getInt("services.idService"));
        servicesDTO.setNomService(rs.getString("services.nomService"));
        servicesDTO.setTypeService(rs.getString("services.typeService"));
        servicesDTO.setMailService(rs.getString("services.mailService"));
        servicesDTO.setTelService(rs.getString("services.telService"));
        servicesDTO.setDateCreation(rs.getTimestamp("services.dateCreation"));
        servicesDTO.setIdAdresse(rs.getInt("services.idAdresse"));
        servicesDTO.setIdSite(rs.getInt("services.idSite"));


        AdressesDTO adressesDTO = new AdressesDTO();
        adressesDTO.setIdAdresse(rs.getInt("adresses.idAdresse"));
        adressesDTO.setNomRue(rs.getString("adresses.nomRue"));
        adressesDTO.setNomBatiment(rs.getString("adresses.nomBatiment"));
        adressesDTO.setNumRue(rs.getInt("adresses.numRue"));
        adressesDTO.setCodePostal(rs.getString("adresses.codePostal"));
        adressesDTO.setVille(rs.getString("adresses.ville"));

//        always.setEmployeeDTO(employeeDTO);
        affiliation.setServicesDTO(servicesDTO);
//        always.setWorksiteDTO(worksiteDTO);
        affiliation.setAdressesDTO(adressesDTO);

        return affiliation;
    };
    public List<Affiliation> getALlInfos(Integer idEmployee, Integer idService, Integer idSite, String nomEmploye, String nomService, String nomSite) {
        List<Affiliation> listAlways = null;
        Affiliation resp = null;

        String sqlQuery = "SELECT employees.*, services.*, sites.*, adresses.* " +
                "FROM employees employees " +
                "JOIN services services ON employees.SERVICES_EMPLOYEE = services.IDSERVICE " +
                "JOIN adresses adresses ON services.ADRESSES_SERVICE = adresses.IDADRESSE " +
                "JOIN sites sites ON services.SITES_OF_SERVICE = sites.IDSITE";

        boolean whereAdded = false; // Utilisé pour gérer l'ajout correct de la clause WHERE

        if (idEmployee != null && idEmployee != 0) {
            sqlQuery += " WHERE employees.idEmploye = " + idEmployee;
            whereAdded = true;
        }
        if (idService != null && idService != 0) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " services.idService = " + idService;
            whereAdded = true;
        }
        if (idSite != null && idSite != 0) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " sites.idSite = " + idSite;
            whereAdded = true;
        }
        if (nomEmploye != null && !nomEmploye.isEmpty()) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " (employees.prenom LIKE '%" + nomEmploye + "%' OR employees.nom LIKE '%" + nomEmploye + "%')";
            whereAdded = true;
        }
        if (nomService != null && !nomService.isEmpty()) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " services.nomService LIKE '%" + nomService + "%'";
            whereAdded = true;
        }
        if (nomSite != null && !nomSite.isEmpty()) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " sites.nomSite LIKE '%" + nomSite + "%'";
        }

        List<AffiliationDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                this.rowMapper
        );

        if (dtos != null && dtos.size() > 0) {
            listAlways = new ArrayList<Affiliation>();

            for (AffiliationDTO dto : dtos) {
                resp = mapperAffiliationAvecAffiliationDTO.DTOToAffiliation(dto);
                listAlways.add(resp);
            }
        }
        return listAlways;
    }

}
