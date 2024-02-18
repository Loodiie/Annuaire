package annuaire.Annuaire.controller.services;
import annuaire.Annuaire.business.services.ServicesBusiness;
import annuaire.Annuaire.controller.services.model.NewServices;
import annuaire.Annuaire.controller.services.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class ServicesController {
    private final String version = "/api/v1";
    private final ServicesBusiness servicesBusiness;

    @Autowired
    public ServicesController(ServicesBusiness servicesBusiness) {
        this.servicesBusiness = servicesBusiness;
    }

    @PostMapping(version + "/services")
    public Services createServiceSite(@RequestBody NewServices services) {
        return servicesBusiness.createServicesService(services);
    }
    @DeleteMapping(version + "/services/{id}")
    public boolean deleteServiceSite(@PathVariable int id){
        return servicesBusiness.deleteServicesService(id);
    }
    @PutMapping(version + "/services/{id}")
    public Services updateServiceSite(@PathVariable int id, @RequestBody NewServices services) {
        return servicesBusiness.updateServicesService(id, services);
    }
    @GetMapping(version + "/services/{id}")
    public Services getOneServiceSite(@PathVariable int id) {
        return servicesBusiness.getOneServicesService(id);
    }
    @GetMapping(version + "/services")
    public List<Services> getAllServicesSite() {
        return servicesBusiness.getAllServicesService();
    }
    @GetMapping(version + "/services/searchServices")
    public List<Services> searchServiceByName(@RequestParam String searchServices) {
        return servicesBusiness.searchServicesByNameService(searchServices);
    }

}
