package annuaire.Annuaire.mapper;

import annuaire.Annuaire.dao.adresses.model.AdressesDTO;
import annuaire.Annuaire.controller.adresses.model.Adresses;
import  annuaire.Annuaire.controller.adresses.model.NewAdresses;

public class MapperAdressesAvecAdressesDTO {

    public AdressesDTO AdressesToDTO(Adresses adresses){
        AdressesDTO dto = new AdressesDTO();
        dto.setIdAdresse(adresses.getIdAdresse());
        dto.setNomRue(adresses.getNomRue());
        dto.setNomBatiment(adresses.getNomBatiment());
        dto.setNumRue(adresses.getNumRue());
        dto.setComplement(adresses.getComplement());
        dto.setCodePostal(adresses.getCodePostal());
        dto.setVille(adresses.getVille());

        return dto;
    }

    public AdressesDTO NewAdressesToDTO(NewAdresses adresses) {
        AdressesDTO dto = new AdressesDTO();
        dto.setNomRue(adresses.getNomRue());
        dto.setNomBatiment(adresses.getNomBatiment());
        dto.setNumRue(adresses.getNumRue());
        dto.setComplement(adresses.getComplement());
        dto.setCodePostal(adresses.getCodePostal());
        dto.setVille(adresses.getVille());

        return dto;
    }

    public Adresses DTOToAdresses (AdressesDTO dto) {
        Adresses adresses = new Adresses();
        adresses.setIdAdresse(dto.getIdAdresse());
        adresses.setNomRue(dto.getNomRue());
        adresses.setNomBatiment(dto.getNomBatiment());
        adresses.setNumRue(dto.getNumRue());
        adresses.setComplement(dto.getComplement());
        adresses.setCodePostal(dto.getCodePostal());
        adresses.setVille(dto.getVille());

        return adresses;
    }
}
