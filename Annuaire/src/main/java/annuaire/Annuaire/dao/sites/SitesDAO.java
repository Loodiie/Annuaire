package annuaire.Annuaire.dao.sites;

import annuaire.Annuaire.mapper.MapperSitesAvecSitesDTO;
import annuaire.Annuaire.controller.sites.model.Sites;
import annuaire.Annuaire.controller.sites.model.NewSites;
import annuaire.Annuaire.dao.sites.model.SitesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository

public class SitesDAO {
    private static final String ID_FIELD = "idSite";
    private static final String NOMSITE_FIELD = "nomSite";
    private static final String TELSITE_FIELD = "telSite";
    private static final String MAILSITE_FIELD = "mailSite";
    private static final String TYPESITE_FIELD = "typeSite";
    private static final String ADRESSEID_FIELD = "idAdresse";

    private final JdbcTemplate jdbcTemplate;
    private final MapperSitesAvecSitesDTO mapperSitesAvecSitesDTO;

    @Autowired
    public SitesDAO(DataSource dataSource, MapperSitesAvecSitesDTO mapperSitesAvecSitesDTO){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapperSitesAvecSitesDTO = mapperSitesAvecSitesDTO;
    }

    private final RowMapper<SitesDTO> rowMapper = (rs, rowNum) -> {
        final SitesDTO sites = new SitesDTO();
        sites.setIdSite(rs.getInt(ID_FIELD));
        sites.setNomSite(rs.getString(NOMSITE_FIELD));
        sites.setTelSite(rs.getString(TELSITE_FIELD));
        sites.setMailSite(rs.getString(MAILSITE_FIELD));
        sites.setTypeSite(rs.getString(TYPESITE_FIELD));
        sites.setIdAdresse(rs.getInt(ADRESSEID_FIELD));

        return sites;
    };

    public Sites create(NewSites sites){
        SitesDTO sites1 = null;
        Sites sites2 = null;

        final String sqlQuery = "INSERT INTO sites (nomSite, telSite, mailSite, typeSIte,idAdresse) VALUES (?,?,?,?,?)";
        int resultCreation = this.jdbcTemplate.update(
                sqlQuery,
                sites.getNomSite(),
                sites.getTelSite(),
                sites.getMailSite(),
                sites.getTypeSite(),
                sites.getIdAdresse()
        );
        if(resultCreation == 1){
            sites1 = mapperSitesAvecSitesDTO.NewSitesToDTO(sites);
            sites2 = mapperSitesAvecSitesDTO.DTOToSites(sites1);
        }
        return sites2;
    }

    public Sites update(int id, NewSites sites){
        SitesDTO sites1 = null;
        Sites sites2 = null;

        final String sqlQuery = "UPDATE sites SET nomSite =?, telSite = ?, mailSite = ?, typeSite = ?, idAdresse = ? WHERE idSite = ?";
        int resultUpdate = this.jdbcTemplate.update(
                sqlQuery,
                sites.getNomSite(),
                sites.getTelSite(),
                sites.getMailSite(),
                sites.getTypeSite(),
                sites.getIdAdresse(),
                id
        );
        if(resultUpdate ==1){
            sites1 = mapperSitesAvecSitesDTO.NewSitesToDTO(sites);
            sites2 = mapperSitesAvecSitesDTO.DTOToSites(sites1);
        }
        return sites2;
    }

    public boolean delete(int id){
        final String sqlQuery = "DELETE FROM sites WHERE idSite = ?";
        int resultDelete = this.jdbcTemplate.update(
                sqlQuery,
                id
        );
        if(resultDelete ==1){
            return true;
        }else {
            return false;
        }
    }

    public Sites getOne(int id){
        Sites sites =null;
        String sqlQuery = "SELECT * FROM sites WHERE idSite=?";
        Object[] param = new Object[]{id};
        List<SitesDTO> dtos = this.jdbcTemplate.query(sqlQuery, param, this.rowMapper);
        if(dtos != null && !dtos.isEmpty()){
            sites = mapperSitesAvecSitesDTO.DTOToSites(dtos.get(0));
        }
        return sites;
    }

    public List<Sites> getAll(){
        List<Sites> listSites = null;
        Sites resp = null;
        String sqlQuery = "SELECT * FROM sites";
        List<SitesDTO> dtos = this.jdbcTemplate.query(sqlQuery, this.rowMapper);

        if(dtos != null && dtos.size() > 0){
            listSites = new ArrayList<Sites>();
            for(SitesDTO dto : dtos){
                resp = mapperSitesAvecSitesDTO.DTOToSites(dto);
                listSites.add(resp);
            }
        }
        return listSites;
    }

    public List<Sites> searchSitesByName(String searchSites){
        List<Sites> listSites = null;
        Sites resp = null;

        String sqlQuery = "SELECT * FROM sites WHERE nomSIte LIKE'%" + searchSites +"%'";
        List<SitesDTO> dtos = this.jdbcTemplate.query(sqlQuery, this.rowMapper);

        if(dtos != null && dtos.size() >0){
            listSites = new ArrayList<Sites>();

            for(SitesDTO dto : dtos){
                resp = mapperSitesAvecSitesDTO.DTOToSites(dto);
                listSites.add(resp);
            }
        }
        return listSites;
    }


}
