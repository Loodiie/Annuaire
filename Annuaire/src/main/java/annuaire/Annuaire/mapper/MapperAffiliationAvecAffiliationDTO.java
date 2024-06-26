package annuaire.Annuaire.mapper;

import annuaire.Annuaire.controller.affiliation.model.Affiliation;
import annuaire.Annuaire.dao.affiliation.model.AffiliationDTO;

public class MapperAffiliationAvecAffiliationDTO {
    private final MapperEmployeesAvecEmployeesDTO mapperEmployees;
    private final MapperSitesAvecSitesDTO mapperSites;
    private final MapperServicesAvecServicesDTO mapperServices;

    public MapperAffiliationAvecAffiliationDTO(MapperServicesAvecServicesDTO mapperServices, MapperSitesAvecSitesDTO mapperSites, MapperEmployeesAvecEmployeesDTO mapperEmployees) {
        this.mapperEmployees = mapperEmployees;
        this.mapperSites = mapperSites;
        this.mapperServices = mapperServices;
    }

    public AffiliationDTO AffiliationToDTO(Affiliation affiliation) {
        AffiliationDTO dto = new AffiliationDTO();
        dto.setEmployeesDTO(mapperEmployees.EmployeesToDTO(affiliation.getEmployees()));
        dto.setServicesDTO(mapperServices.ServicesToDTO(affiliation.getServices()));
        dto.setSitesDTO(mapperSites.SitesToDTO(affiliation.getSites()));
        return dto;
    }

    public Affiliation DTOToAffiliation (AffiliationDTO dto) {
        Affiliation affiliation = new Affiliation();
        affiliation.setEmployees(mapperEmployees.DTOToEmployees(dto.getEmployeesDTO()));
        affiliation.setServices(mapperServices.DTOToService(dto.getServicesDTO()));
        affiliation.setSites(mapperSites.DTOToSites(dto.getSitesDTO()));
        return affiliation;
    }
}
