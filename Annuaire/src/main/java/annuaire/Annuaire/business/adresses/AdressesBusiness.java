package annuaire.Annuaire.business.adresses;

import annuaire.Annuaire.dao.adresses.AdressesDAO;
import annuaire.Annuaire.controller.adresses.model.Adresses;
import annuaire.Annuaire.controller.adresses.model.NewAdresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressesBusiness {
    private final AdressesDAO adressesDAO;

    @Autowired
    public AdressesBusiness(AdressesDAO adressesDAO){ this.adressesDAO = adressesDAO;}

    public Adresses createAdressesService(NewAdresses adresses){
        return adressesDAO.create(adresses);
    }

    public Boolean deleteAdressesService(int id){
        return adressesDAO.delete(id);
    }

    public Adresses updateAdressesService(int id, NewAdresses adresses){
        return adressesDAO.update(id, adresses);
    }

    public Adresses getOneAdressesService(int id){
        return adressesDAO.getOne(id);
    }

    public List<Adresses> getAllAdressesService(){
        return adressesDAO.getAll();
    }

}
