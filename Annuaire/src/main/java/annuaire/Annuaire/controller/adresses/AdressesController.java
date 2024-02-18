package annuaire.Annuaire.controller.adresses;

import annuaire.Annuaire.business.adresses.AdressesBusiness;
import annuaire.Annuaire.controller.adresses.model.Adresses;
import annuaire.Annuaire.controller.adresses.model.NewAdresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
public class AdressesController {
    private final String version = "/api/v1";
    private final AdressesBusiness adressesBusiness;

    @Autowired
    public AdressesController(AdressesBusiness adressesBusiness){this.adressesBusiness = adressesBusiness;}

    @PostMapping(version + "/adresses")
    public Adresses createAdresses(@RequestBody NewAdresses adresses){
        return adressesBusiness.createAdressesService(adresses);
    }
    @DeleteMapping(version + "/adresses/{id}")
    public boolean deleteAdresses(@PathVariable int id){
        return adressesBusiness.deleteAdressesService(id);
    }
    @PutMapping(version + "/adresses/{id}")
    public Adresses updateAddress(@PathVariable int id, @RequestBody NewAdresses adresses) {
        return adressesBusiness.updateAdressesService(id, adresses);
    }
    @GetMapping(version + "/adresses/{id}")
    public Adresses getOneAddress(@PathVariable int id) {
        return adressesBusiness.getOneAdressesService(id);
    }
    @GetMapping(version + "/adresses")
    public List<Adresses> getAllAdresses()
    {
        return adressesBusiness.getAllAdressesService();
    }

}
