package annuaire.Annuaire.business.affiliation;

import annuaire.Annuaire.controller.affiliation.model.Affiliation;
import annuaire.Annuaire.dao.adresses.AdressesDAO;
import annuaire.Annuaire.dao.affiliation.AffiliationDAO;
//import annuaire.Annuaire.dao.employees.EmployeesDAO;
import annuaire.Annuaire.dao.services.ServicesDAO;
//import annuaire.Annuaire.dao.sites.SitesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliationBusiness {
    private final AffiliationDAO affiliationDAO;
    //private final EmployeesDAO employeesDAO;
    private final ServicesDAO servicesDAO;
    //private final sites sitesDAO;
    private final AdressesDAO adressesDAO;

    @Autowired
    public AffiliationBusiness (AffiliationDAO affiliationDAO, ServicesDAO servicesDAO, AdressesDAO adressesDAO) { //RAJOUTER SITE ET EMPLOYEES
        this.affiliationDAO = affiliationDAO;
        this.servicesDAO = servicesDAO;
        this.adressesDAO = adressesDAO;
    }

    public List<Affiliation> getALlInfosService(Integer idEmploye, Integer idService, Integer idSite, String nomEmploye, String nomService, String nomSite) {
        return affiliationDAO.getALlInfos(idEmploye, idService, idSite, nomEmploye, nomService, nomSite);
    }

}
