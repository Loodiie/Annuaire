package annuaire.Annuaire.controller.services.model;

import java.sql.Timestamp;

public class Services {
    int idService;
    String nomService;
    String typeService;
    String mailService;
    String telService;
    Timestamp dateCreation;
    int idSite;

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getMailService() {
        return mailService;
    }

    public void setMailService(String mailService) {
        this.mailService = mailService;
    }

    public String getTelService() {
        return telService;
    }

    public void setTelService(String telService) {
        this.telService = telService;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }
}
