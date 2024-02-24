package annuaire.Annuaire.dao.services;
import annuaire.Annuaire.dao.services.model.ServicesDTO;
import annuaire.Annuaire.controller.services.model.NewServices;
import annuaire.Annuaire.controller.services.model.Services;
import annuaire.Annuaire.mapper.MapperServicesAvecServicesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicesDAO {
    private static final String ID_FIELD = "idService";
    private static final String NOMSERVICE_FIELD = "nomService";
    private static final String TYPESERVICE_FIELD = "typeService";
    private static final String MAILSERVICE_FIELD = "mailService";
    private static final String TELSERVICE_FIELD = "telService";
    private static final String DATECREATION_FIELD = "dateCreation";
    private static final String SITESERVICE_FIELD = "idSite";

    private final JdbcTemplate jdbcTemplate;
    private final MapperServicesAvecServicesDTO mapperServicesAvecServicesDTO;

    @Autowired
    public ServicesDAO(DataSource dataSource, MapperServicesAvecServicesDTO mapperServicesAvecServicesDTO) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapperServicesAvecServicesDTO = mapperServicesAvecServicesDTO;
    }

    private final RowMapper<ServicesDTO> rowMapper = (rs, rowNum) -> {
        final ServicesDTO service = new ServicesDTO();
        service.setIdService(rs.getInt(ID_FIELD));
        service.setNomService(rs.getString(NOMSERVICE_FIELD));
        service.setTypeService(rs.getString(TYPESERVICE_FIELD));
        service.setMailService(rs.getString(MAILSERVICE_FIELD));
        service.setTelService(rs.getString(TELSERVICE_FIELD));
        service.setDateCreation(rs.getTimestamp(DATECREATION_FIELD));
        service.setIdSite(rs.getInt(SITESERVICE_FIELD));

        return service;
    };

    public Services getOne (int id) {
        Services service = null;
        String sqlQuery = "SELECT * FROM services WHERE idService = ?";
        Object[] param = new Object[]{id};
        List<ServicesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                param,
                this.rowMapper
        );

        if (dtos != null && !dtos.isEmpty()) {
            service = mapperServicesAvecServicesDTO.DTOToService(dtos.get(0));
        }
        return service;
    }

    public List<Services> getAll () {
        List<Services> listService = null;
        Services resp = null;

        String sqlQuery = "SELECT * FROM services";

        List<ServicesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                this.rowMapper
        );

        if (dtos != null && dtos.size() > 0) {
            listService = new ArrayList<Services>();

            for (ServicesDTO dto : dtos) {
                resp = mapperServicesAvecServicesDTO.DTOToService(dto);
                listService.add(resp);
            }
        }
        return listService;
    }

    public Services create(NewServices service){
        ServicesDTO service1 = null;
        Services service2 = null;

        final String sqlQuery = "INSERT INTO services (nomService, typeService, mailService, telService, dateCreation, idSite) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int resultCreation = this.jdbcTemplate.update(
                sqlQuery,
                service.getNomService(),
                service.getTypeService(),
                service.getMailService(),
                service.getTelService(),
                service.getDateCreation(),
                service.getIdSite()
        );

        if (resultCreation == 1) {
            service1 = mapperServicesAvecServicesDTO.NewServicesDTO(service);
            service2 = mapperServicesAvecServicesDTO.DTOToService(service1);
        }
        return service2;
    }

    public Services update(int id, NewServices service){
        ServicesDTO service1 = null;
        Services service2 = null;

        final String sqlQuery = "UPDATE services SET nomService = ?, typeService = ?, mailService = ?, telService = ?, dateCreation = ?, idSite = ? WHERE idService = ?";
        int resultUpdate = this.jdbcTemplate.update(
                sqlQuery,
                service.getNomService(),
                service.getTypeService(),
                service.getMailService(),
                service.getTelService(),
                service.getDateCreation(),
                service.getIdSite(),
                id
        );

        if (resultUpdate == 1) {
            service1 = mapperServicesAvecServicesDTO.NewServicesDTO(service);
            service2 = mapperServicesAvecServicesDTO.DTOToService(service1);
        }
        return service2;
    }

    public boolean delete(int id) {
        final String sqlQuery = "DELETE FROM services WHERE idService = ?";
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

    public List<Services> searchServiceSiteByName (String searchServiceSite) {
        List<Services> listService = null;
        Services resp = null;

        String sqlQuery = "SELECT * FROM services WHERE nomService LIKE '%" + searchServiceSite +"%'" ;

        List<ServicesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                this.rowMapper
        );

        if (dtos != null && dtos.size() > 0) {
            listService = new ArrayList<Services>();

            for (ServicesDTO dto : dtos) {
                resp = mapperServicesAvecServicesDTO.DTOToService(dto);
                listService.add(resp);
            }
        }
        return listService;
    }

}
