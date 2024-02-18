package annuaire.Annuaire.controller.sites;

import annuaire.Annuaire.business.sites.SitesBusiness;
import annuaire.Annuaire.controller.sites.model.Sites;
import annuaire.Annuaire.controller.sites.model.NewSites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
public class SitesController {
    private final String version = "/api/v1";
    private final SitesBusiness sitesBusiness;

    @Autowired
    public SitesController(SitesBusiness sitesBusiness) {
        this.sitesBusiness = sitesBusiness;
    }

    @PostMapping(version + "/sites")
    public Sites createSites(@RequestBody NewSites sites) {
        return sitesBusiness.createSitesService(sites);
    }
    @DeleteMapping(version + "/sites/{id}")
    public boolean deleteSites(@PathVariable int id){
        return sitesBusiness.deleteSitesService(id);
    }
    @PutMapping(version + "/sites/{id}")
    public Sites updateSites(@PathVariable int id, @RequestBody NewSites sites) {
        return sitesBusiness.updateSitesService(id, sites);
    }
    @GetMapping(version + "/sites/{id}")
    public Sites getOneSites(@PathVariable int id) {
        return sitesBusiness.getOneSitesService(id);
    }
    @GetMapping(version + "/sites")
    public List<Sites> getAllSites() {
        return sitesBusiness.getAllSitesService();
    }
    @GetMapping(version + "/sites/searchSites")
    public List<Sites> searchSitesByName(@RequestParam String searchSites) {
        return sitesBusiness.searchSitesByNameService(searchSites);
    }

}
