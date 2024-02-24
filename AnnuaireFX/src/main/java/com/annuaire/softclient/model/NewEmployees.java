package com.annuaire.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class NewEmployees {
    private final SimpleStringProperty nomEmploye;
    private final SimpleStringProperty prenomEmploye;
    private final SimpleStringProperty posteEmploye;
    private final SimpleStringProperty fixeEmploye;
    private final SimpleStringProperty mailEmploye;
    private final SimpleObjectProperty<Timestamp> dateNaissance;
    private final SimpleObjectProperty<Timestamp> dateEmbauche;
    private final SimpleBooleanProperty admin;
    private final SimpleIntegerProperty idService;
    public NewEmployees(
            @JsonProperty("nomEmploye") String nomEmploye,
            @JsonProperty("prenomEmploye") String prenomEmploye,
            @JsonProperty("posteEmploye") String posteEmploye,
            @JsonProperty("fixeEmploye") String fixeEmploye,
            @JsonProperty("mailEmploye") String mailEmploye,
            @JsonProperty("dateNaissance") Timestamp dateNaissance,
            @JsonProperty("dateEmbauche") Timestamp dateEmbauche,
            @JsonProperty("admin") boolean admin,
            @JsonProperty("idService") int idService) {
        this.nomEmploye = new SimpleStringProperty(nomEmploye);
        this.prenomEmploye = new SimpleStringProperty(prenomEmploye);
        this.posteEmploye = new SimpleStringProperty(posteEmploye);
        this.fixeEmploye = new SimpleStringProperty(fixeEmploye);
        this.mailEmploye = new SimpleStringProperty(mailEmploye);
        this.dateNaissance = new SimpleObjectProperty<>(dateNaissance);
        this.dateEmbauche = new SimpleObjectProperty<>(dateEmbauche);
        this.admin = new SimpleBooleanProperty(admin);
        this.idService = new SimpleIntegerProperty(idService);
    }

    public String getNomEmploye() {
        return nomEmploye.get();
    }

    public SimpleStringProperty nomEmployeProperty() {
        return nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye.set(nomEmploye);
    }

    public String getPrenomEmploye() {
        return prenomEmploye.get();
    }

    public SimpleStringProperty prenomEmployeProperty() {
        return prenomEmploye;
    }

    public void setPrenomEmploye(String prenomEmploye) {
        this.prenomEmploye.set(prenomEmploye);
    }

    public String getPosteEmploye() {
        return posteEmploye.get();
    }

    public SimpleStringProperty posteEmployeProperty() {
        return posteEmploye;
    }

    public void setPosteEmploye(String posteEmploye) {
        this.posteEmploye.set(posteEmploye);
    }

    public String getFixeEmploye() {
        return fixeEmploye.get();
    }

    public SimpleStringProperty fixeEmployeProperty() {
        return fixeEmploye;
    }

    public void setFixeEmploye(String fixeEmploye) {
        this.fixeEmploye.set(fixeEmploye);
    }

    public String getMailEmploye() {
        return mailEmploye.get();
    }

    public SimpleStringProperty mailEmployeProperty() {
        return mailEmploye;
    }

    public void setMailEmploye(String mailEmploye) {
        this.mailEmploye.set(mailEmploye);
    }

    public Timestamp getDateNaissance() {
        return dateNaissance.get();
    }

    public SimpleObjectProperty<Timestamp> dateNaissanceProperty() {
        return dateNaissance;
    }

    public void setDateNaissance(Timestamp dateNaissance) {
        this.dateNaissance.set(dateNaissance);
    }

    public Timestamp getDateEmbauche() {
        return dateEmbauche.get();
    }

    public SimpleObjectProperty<Timestamp> dateEmbaucheProperty() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Timestamp dateEmbauche) {
        this.dateEmbauche.set(dateEmbauche);
    }

    public boolean isAdmin() {
        return admin.get();
    }

    public SimpleBooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public int getIdService() {
        return idService.get();
    }

    public SimpleIntegerProperty idServiceProperty() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService.set(idService);
    }
}