package annuaire.Annuaire.controller.affiliation;

import annuaire.Annuaire.business.affiliation.AffiliationBusiness;
import annuaire.Annuaire.controller.affiliation.model.Affiliation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class AffiliationController {
    private final String version = "/api/v1";
    private final AffiliationBusiness affiliationBusiness;

    @Autowired
    public AffiliationController(AffiliationBusiness affiliationBusiness){
        this.affiliationBusiness = affiliationBusiness;
    }

    @GetMapping(version +"/affiliation/allInfos")
    public List<Affiliation> getAllInfos(
            @RequestParam(value = "idEmploye", required = false) Integer idEmploye,
            @RequestParam(value = "idService", required = false) Integer idService,
            @RequestParam(value = "idSite", required = false) Integer idSite,
            @RequestParam(value = "nomEmploye", required = false) String nomEmploye,
            @RequestParam(value = "nomService", required = false) String nomService,
            @RequestParam(value = "nomSite", required = false) String nomSite
    ) {
        return affiliationBusiness.getALlInfosService(idEmploye, idService, idSite, nomEmploye, nomService, nomSite);
    }

}
