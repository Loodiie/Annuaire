package annuaire.Annuaire.business.sites;

import annuaire.Annuaire.dao.sites.SitesDAO;
import annuaire.Annuaire.controller.sites.model.Sites;
import annuaire.Annuaire.controller.sites.model.NewSites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitesBusiness {
    private final SitesDAO sitesDAO;

    @Autowired
    public SitesBusiness(SitesDAO sitesDAO){
        this.sitesDAO = sitesDAO;
    }
    public Sites createSitesService (NewSites sites){
        return sitesDAO.create(sites);
    }
    public boolean deleteSitesService (int id) {
        return sitesDAO.delete(id);
    }
    public Sites updateSitesService (int id, NewSites sites){
        return sitesDAO.update(id, sites);
    }
    public Sites getOneSitesService(int id) {
        return sitesDAO.getOne(id);
    }
    public List<Sites> getAllSitesService() {
        return sitesDAO.getAll();
    }
    public List<Sites> searchSitesByNameService(String searchSites) {
        return sitesDAO.searchSitesByName(searchSites);
    }

}
