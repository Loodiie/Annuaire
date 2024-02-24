package annuaire.Annuaire.dao.affiliation;

import annuaire.Annuaire.controller.affiliation.model.Affiliation;
import annuaire.Annuaire.dao.affiliation.model.AffiliationDTO;
import annuaire.Annuaire.dao.employees.model.EmployeesDTO;
import annuaire.Annuaire.dao.services.model.ServicesDTO;
import annuaire.Annuaire.dao.sites.model.SitesDTO;
import annuaire.Annuaire.mapper.MapperAffiliationAvecAffiliationDTO;
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

        EmployeesDTO employeesDTO = new EmployeesDTO();
        employeesDTO.setIdEmployee(rs.getInt("employees.idEmploye"));
        employeesDTO.setNomEmployee(rs.getString("employees.nomEmploye"));
        employeesDTO.setPrenomEmployee(rs.getString("employees.prenomEmploye"));
        employeesDTO.setIdService(rs.getInt("employees.idService"));
        employeesDTO.setPosteEmployee(rs.getString("employees.posteEmploye"));
        employeesDTO.setFixeEmployee(rs.getString("employees.fixeEmploye"));
        employeesDTO.setMailEmployee(rs.getString("employees.mailEmploye"));
        employeesDTO.setDateNaissance(rs.getTimestamp("employees.dateNaissance"));
        employeesDTO.setDateEmbauche(rs.getTimestamp("employees.dateEmbauche"));
        employeesDTO.setAdmin(rs.getBoolean("employees.admin"));

        SitesDTO sitesDTO = new SitesDTO();
        sitesDTO.setIdSite(rs.getInt("sites.idSite"));
        sitesDTO.setNomSite(rs.getString("sites.nomSite"));
        sitesDTO.setTelSite(rs.getString("sites.telSite"));
        sitesDTO.setMailSite(rs.getString("sites.mailSite"));
        sitesDTO.setTypeSite(rs.getString("sites.typeSite"));
        sitesDTO.setVilleSite(rs.getString("sites.villeSite"));


        ServicesDTO servicesDTO = new ServicesDTO();
        servicesDTO.setIdService(rs.getInt("services.idService"));
        servicesDTO.setNomService(rs.getString("services.nomService"));
        servicesDTO.setTypeService(rs.getString("services.typeService"));
        servicesDTO.setMailService(rs.getString("services.mailService"));
        servicesDTO.setTelService(rs.getString("services.telService"));
        servicesDTO.setDateCreation(rs.getTimestamp("services.dateCreation"));
        servicesDTO.setIdSite(rs.getInt("services.idSite"));

        affiliation.setEmployeesDTO(employeesDTO);
        affiliation.setServicesDTO(servicesDTO);
        affiliation.setSitesDTO(sitesDTO);

        return affiliation;
    };
    public List<Affiliation> getAllInfos(Integer idEmployee, Integer idService, Integer idSite, String nomEmploye, String nomService, String nomSite ) {
        List<Affiliation> listAffiliations = null;
        Affiliation resp = null;

        String sqlQuery = "SELECT employees.*, services.*, sites.*" +
                "FROM employees " +
                "JOIN services ON employees.idService = services.IdService " +
                "JOIN sites ON services.idSite = sites.IdSite";

        boolean whereAdded = false; // Utilisé pour gérer l'ajout correct de la clause WHERE

        if (idEmployee != null && idEmployee != 0) {
            sqlQuery += " WHERE employees.idEmploye = " + idEmployee;
            whereAdded = true;
        }
        if (idService != null && idService != 0) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " services.IdService = " + idService;
            whereAdded = true;
        }
        if (idSite != null && idSite != 0) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " sites.IdSite = " + idSite;
            whereAdded = true;
        }
        if (nomEmploye != null && !nomEmploye.isEmpty()) {
            sqlQuery += (whereAdded ? " AND" : " WHERE") + " (employees.nomEmploye LIKE '%" + nomEmploye + "%')";
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
            listAffiliations = new ArrayList<>();

            for (AffiliationDTO dto : dtos) {
                resp = mapperAffiliationAvecAffiliationDTO.DTOToAffiliation(dto);
                listAffiliations.add(resp);
            }
        }
        return listAffiliations;
    }

}
