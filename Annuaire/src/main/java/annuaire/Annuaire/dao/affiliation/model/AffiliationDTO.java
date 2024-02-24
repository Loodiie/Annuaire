package annuaire.Annuaire.dao.affiliation.model;


import annuaire.Annuaire.dao.services.model.ServicesDTO;
import annuaire.Annuaire.dao.sites.model.SitesDTO;
import annuaire.Annuaire.dao.employees.model.EmployeesDTO;

public class AffiliationDTO {

    ServicesDTO servicesDTO;
    SitesDTO sitesDTO;
    EmployeesDTO employeesDTO;


    public ServicesDTO getServicesDTO() {
        return servicesDTO;
    }

    public void setServicesDTO(ServicesDTO servicesDTO) {
        this.servicesDTO = servicesDTO;
    }

    public SitesDTO getSitesDTO() {
        return sitesDTO;
    }

    public void setSitesDTO(SitesDTO sitesDTO) {
        this.sitesDTO = sitesDTO;
    }

    public EmployeesDTO getEmployeesDTO() {
        return employeesDTO;
    }

    public void setEmployeesDTO(EmployeesDTO employeesDTO) {
        this.employeesDTO = employeesDTO;
    }
}
