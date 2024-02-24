package com.annuaire.softclient.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class NewServices {

    private final SimpleStringProperty nomService;
    private final SimpleStringProperty typeService;
    private final SimpleStringProperty mailService;
    private final SimpleStringProperty telService;
    private final SimpleObjectProperty<Timestamp> dateCreation;
    private final SimpleIntegerProperty idAdresse;
    private final SimpleIntegerProperty idSite;

    public NewServices(
            @JsonProperty("nomService") String nomService,
            @JsonProperty("typeService") String typeService,
            @JsonProperty("mailService") String mailService,
            @JsonProperty("telService") String telService,
            @JsonProperty("dateCreation") Timestamp dateCreation,
            @JsonProperty("idAdresse") int idAdresse,
            @JsonProperty("idSite") int idSite
    ) {
        this.nomService = new SimpleStringProperty(nomService);
        this.typeService = new SimpleStringProperty(typeService);
        this.mailService = new SimpleStringProperty(mailService);
        this.telService = new SimpleStringProperty(telService);
        this.dateCreation = new SimpleObjectProperty<>(dateCreation);
        this.idAdresse = new SimpleIntegerProperty(idAdresse);
        this.idSite = new SimpleIntegerProperty(idSite);
    }
    public String getNomService() {
        return nomService.get();
    }

    public SimpleStringProperty nomServiceProperty() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService.set(nomService);
    }

    public String getTypeService() {
        return typeService.get();
    }

    public SimpleStringProperty typeServiceProperty() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService.set(typeService);
    }

    public String getMailService() {
        return mailService.get();
    }

    public SimpleStringProperty mailServiceProperty() {
        return mailService;
    }

    public void setMailService(String mailService) {
        this.mailService.set(mailService);
    }

    public String getTelService() {
        return telService.get();
    }

    public SimpleStringProperty telServiceProperty() {
        return telService;
    }

    public void setTelService(String telService) {
        this.telService.set(telService);
    }

    public Timestamp getDateCreation() {
        return dateCreation.get();
    }

    public SimpleObjectProperty<Timestamp> dateCreationProperty() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation.set(dateCreation);
    }

    public int getIdAdresse() {
        return idAdresse.get();
    }

    public SimpleIntegerProperty idAdresseProperty() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse.set(idAdresse);
    }

    public int getIdSite() {
        return idSite.get();
    }

    public SimpleIntegerProperty idSiteProperty() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite.set(idSite);
    }
}
