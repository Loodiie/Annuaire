package annuaire.Annuaire.business.services;
import annuaire.Annuaire.dao.services.ServicesDAO;
import annuaire.Annuaire.controller.services.model.NewServices;
import annuaire.Annuaire.controller.services.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesBusiness {
    private final ServicesDAO servicesDAO;

    @Autowired
    public ServicesBusiness(ServicesDAO servicesDAO) {
        this.servicesDAO = servicesDAO;
    }
    public Services createServicesService (NewServices services){
        return servicesDAO.create(services);
    }
    public boolean deleteServicesService (int id) {
        return servicesDAO.delete(id);
    }
    public Services updateServicesService (int id, NewServices services) {
        return servicesDAO.update(id, services);
    }
    public Services getOneServicesService (int id) {
        return servicesDAO.getOne(id);
    }
    public List<Services> getAllServicesService() {
        return servicesDAO.getAll();
    }
    public List<Services> searchServicesByNameService(String searchServiceSite) {
        return servicesDAO.searchServiceSiteByName(searchServiceSite);
    }

}
