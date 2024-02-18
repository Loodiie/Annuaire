package annuaire.Annuaire.dao.adresses;

import annuaire.Annuaire.dao.adresses.model.AdressesDTO;
import annuaire.Annuaire.controller.adresses.model.Adresses;
import annuaire.Annuaire.controller.adresses.model.NewAdresses;
import annuaire.Annuaire.mapper.MapperAdressesAvecAdressesDTO;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class AdressesDAO {

    private static final String ID_FIELD = "idAdresse";
    private static final String NOMRUE_FIELD = "nomRue";
    private static final String NOMBATIMENT_FIELD = "nomBatiment";
    private static final String NUMRUE_FIELD = "numRue";
    private static final String COMPLEMENT_FIELD = "complement";
    private static final String CODEPOSTAL_FIELD = "codePostal";
    private static final String VILLE_FIELD = "ville";

    private final JdbcTemplate jdbcTemplate;
    private final MapperAdressesAvecAdressesDTO mapperAdressesAvecAdressesDTO;

    @Autowired
    public AdressesDAO(DataSource dataSource, MapperAdressesAvecAdressesDTO mapperAdressesAvecAdressesDTO){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapperAdressesAvecAdressesDTO = mapperAdressesAvecAdressesDTO;
    }

    private final RowMapper<AdressesDTO> rowMapper  = (rs, rowNum) -> {
        final AdressesDTO adresses = new AdressesDTO();
        adresses.setIdAdresse(rs.getInt(ID_FIELD));
        adresses.setNomRue(rs.getString(NOMRUE_FIELD));
        adresses.setNomBatiment(rs.getString(NOMBATIMENT_FIELD));
        adresses.setNumRue(rs.getInt(NUMRUE_FIELD));
        adresses.setComplement(rs.getString(COMPLEMENT_FIELD));
        adresses.setCodePostal(rs.getString(CODEPOSTAL_FIELD));
        adresses.setVille(rs.getString(VILLE_FIELD));
        return adresses;
    };

    public Adresses create(NewAdresses adresses) {
        //Insère une nouvelle adresse dans la db
        //Pas besoin d'initialiser l'ID puisqu'il est en auto_incr dans la BDD

        AdressesDTO adresses1 = null;
        Adresses adresses2 = null;

        final String sqlQuery = "INSERT INTO address (nomRue, nomBatiment, numRue, complement, codePostal, ville) VALUES (?,?,?,?,?,?)";
        int resultCreation = this.jdbcTemplate.update(
                sqlQuery,
                adresses.getNomRue(),
                adresses.getNomBatiment(),
                adresses.getNumRue(),
                adresses.getComplement(),
                adresses.getCodePostal(),
                adresses.getVille()
        );

        if (resultCreation == 1) { //J'attends un résultat de type Address
            //Mais comme je récupère un objet de type AddressDTO, il faut que je le retransforme en Address
            //Donc double mapping, mais je peux créer un mapper qui fait NewAddressToAddress
            adresses1 = mapperAdressesAvecAdressesDTO.NewAdressesToDTO(adresses);
            adresses2 = mapperAdressesAvecAdressesDTO.DTOToAdresses(adresses1);
        }

        return adresses2;

    }

    public Adresses update(int id, NewAdresses adresses) {
        //Met à jour l'ID indiqué dans les paramètres
        AdressesDTO adresses1 = null;
        Adresses adresses2 = null;

        final String sqlQuery = "UPDATE adresses SET nomRue = ?, nomBatiment = ?, numRue = ?, complement = ?, codePostal = ?, ville = ? WHERE idAdresse = ?";
        int resultUpdate = this.jdbcTemplate.update (
                sqlQuery,
                adresses.getNomRue(),
                adresses.getNomBatiment(),
                adresses.getNumRue(),
                adresses.getComplement(),
                adresses.getCodePostal(),
                adresses.getVille(),
                id
        );

        if (resultUpdate == 1){
            adresses1 = mapperAdressesAvecAdressesDTO.NewAdressesToDTO(adresses);
            adresses2 = mapperAdressesAvecAdressesDTO.DTOToAdresses(adresses1);
        }
        return adresses2;
    }

    public boolean delete(int id) {
        final String sqlQuery = "DELETE FROM adresses WHERE idAdresse = ?";
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



    public Adresses getOne(int id){
        Adresses adresses =null;
        String sqlQuery = "SELECT * FROM adresses WHERE idAdresse=" + id;
        List<AdressesDTO> dtos = this.jdbcTemplate.query(sqlQuery, this.rowMapper);
        if (dtos != null && dtos.size() == 1) {
            adresses = mapperAdressesAvecAdressesDTO.DTOToAdresses(dtos.get(0));
        }

        return adresses;
    }

    public List<Adresses> getAll() {
        //Lis les informations de toutes les adresses de la BDD
        List<Adresses> listAdresses = null;
        Adresses resp = null;

        String sqlQuery = "SELECT * FROM address";

        List<AdressesDTO> dtos = this.jdbcTemplate.query(
                sqlQuery,
                this.rowMapper
        );

        if (dtos != null && dtos.size() > 0) {
            listAdresses = new ArrayList<Adresses>();

            for(AdressesDTO dto : dtos) {
                resp = mapperAdressesAvecAdressesDTO.DTOToAdresses(dto);
                listAdresses.add(resp);
            }
        }
        return listAdresses;
    }

}
