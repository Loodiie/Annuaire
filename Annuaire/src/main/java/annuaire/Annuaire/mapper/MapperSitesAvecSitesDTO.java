package annuaire.Annuaire.mapper;

import annuaire.Annuaire.dao.sites.model.SitesDTO;
import annuaire.Annuaire.controller.sites.model.Sites;
import annuaire.Annuaire.controller.sites.model.NewSites;
public class MapperSitesAvecSitesDTO {

    public SitesDTO SitesToDTO (Sites sites) {
        SitesDTO dto = new SitesDTO();
        dto.setIdSite(sites.getIdSite());
        dto.setNomSite(sites.getNomSite());
        dto.setTelSite(sites.getTelSite());
        dto.setMailSite(sites.getMailSite());
        dto.setTypeSite(sites.getTypeSite());
        dto.setVilleSite(sites.getVilleSite());

        return dto;
    }

    public SitesDTO NewSitesToDTO(NewSites sites) {
        SitesDTO dto = new SitesDTO();
        dto.setNomSite(sites.getNomSite());
        dto.setTelSite(sites.getTelSite());
        dto.setMailSite(sites.getMailSite());
        dto.setTypeSite(sites.getTypeSite());
        dto.setVilleSite(sites.getVilleSite());

        return dto;
    }

    public Sites DTOToSites (SitesDTO dto) {
        Sites sites = new Sites();
        sites.setIdSite(dto.getIdSite());
        sites.setNomSite(dto.getNomSite());
        sites.setTelSite(dto.getTelSite());
        sites.setMailSite(dto.getMailSite());
        sites.setTypeSite(dto.getTypeSite());
        sites.setVilleSite(dto.getVilleSite());

        return sites;
    }

}
