package annuaire.Annuaire.mapper;

import annuaire.Annuaire.dao.services.model.ServicesDTO;
import annuaire.Annuaire.controller.services.model.Services;
import annuaire.Annuaire.controller.services.model.NewServices;

public class MapperServicesAvecServicesDTO {
    public ServicesDTO ServicesToDTO(Services services) {
        ServicesDTO dto = new ServicesDTO();
        dto.setIdService(services.getIdService());
        dto.setNomService(services.getNomService());
        dto.setTypeService(services.getTypeService());
        dto.setTelService(services.getTelService());
        dto.setMailService(services.getMailService());
        dto.setDateCreation(services.getDateCreation());
        dto.setIdAdresse(services.getIdAdresse());
        dto.setIdSite(services.getIdSite());

        return dto;
    }

    public ServicesDTO NewServicesDTO(NewServices service) {
        ServicesDTO dto = new ServicesDTO();
        dto.setNomService(service.getNomService());
        dto.setTypeService(service.getTypeService());
        dto.setTelService(service.getTelService());
        dto.setMailService(service.getMailService());
        dto.setDateCreation(service.getDateCreation());
        dto.setIdAdresse(service.getIdAdresse());
        dto.setIdSite(service.getIdSite());

        return dto;
    }

    public Services DTOToService(ServicesDTO dto) {
        Services service = new Services();
        service.setIdService(dto.getIdService());
        service.setNomService(dto.getNomService());
        service.setTypeService(dto.getTypeService());
        service.setTelService(dto.getTelService());
        service.setMailService(dto.getMailService());
        service.setDateCreation(dto.getDateCreation());
        service.setIdAdresse(dto.getIdAdresse());
        service.setIdSite(dto.getIdSite());

        return service;

    }
}