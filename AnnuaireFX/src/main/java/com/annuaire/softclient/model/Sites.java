package com.annuaire.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sites {
    private final SimpleIntegerProperty idSite;
    private final SimpleStringProperty nomSite;
    private final SimpleStringProperty telSite;
    private final SimpleStringProperty mailSite;
    private final SimpleStringProperty typeSite;
    private final SimpleStringProperty villeSite;

    public Sites(
            @JsonProperty("idSite") int idSite,
            @JsonProperty("nomSite") String nomSite,
            @JsonProperty("telSite") String telSite,
            @JsonProperty("mailSite") String mailSite,
            @JsonProperty("typeSite") String typeSite,
            @JsonProperty("villeSite") String villeSite
    ) {
        this.idSite = new SimpleIntegerProperty(idSite);
        this.nomSite = new SimpleStringProperty(nomSite);
        this.telSite = new SimpleStringProperty(telSite);
        this.mailSite = new SimpleStringProperty(mailSite);
        this.typeSite = new SimpleStringProperty(typeSite);
        this.villeSite = new SimpleStringProperty(villeSite);
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

    public String getNomSite() {
        return nomSite.get();
    }

    public SimpleStringProperty nomSiteProperty() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite.set(nomSite);
    }

    public String getTelSite() {
        return telSite.get();
    }

    public SimpleStringProperty telSiteProperty() {
        return telSite;
    }

    public void setTelSite(String telSite) {
        this.telSite.set(telSite);
    }

    public String getMailSite() {
        return mailSite.get();
    }

    public SimpleStringProperty mailSiteProperty() {
        return mailSite;
    }

    public void setMailSite(String mailSite) {
        this.mailSite.set(mailSite);
    }

    public String getTypeSite() {
        return typeSite.get();
    }

    public SimpleStringProperty typeSiteProperty() {
        return typeSite;
    }

    public void setTypeSite(String typeSite) {
        this.typeSite.set(typeSite);
    }

    public String getVilleSite() {
        return villeSite.get();
    }

    public SimpleStringProperty villeSiteProperty() {
        return villeSite;
    }

    public void setVilleSite(String villeSite) {
        this.villeSite.set(villeSite);
    }
}

