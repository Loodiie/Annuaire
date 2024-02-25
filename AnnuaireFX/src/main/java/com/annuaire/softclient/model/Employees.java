package com.annuaire.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class Employees {
    private final SimpleIntegerProperty idEmploye;
    private final SimpleStringProperty nomEmployee;
    private final SimpleStringProperty prenomEmployee;
    private final SimpleIntegerProperty idService;
    private final SimpleStringProperty posteEmployee;
    private final SimpleStringProperty fixeEmployee;
    private final SimpleStringProperty mailEmployee;
    private final SimpleObjectProperty<Timestamp> dateNaissance;
    private final SimpleObjectProperty<Timestamp> dateEmbauche;
    private final SimpleBooleanProperty admin;


    public Employees(
            @JsonProperty("idEmploye") int idEmploye,
            @JsonProperty("nomEmployee") String nomEmployee,
            @JsonProperty("prenomEmployee") String prenomEmployee,
            @JsonProperty("posteEmployee") String posteEmployee,
            @JsonProperty("fixeEmployee") String fixeEmployee,
            @JsonProperty("mailEmployee") String mailEmployee,
            @JsonProperty("dateNaissance") Timestamp dateNaissance,
            @JsonProperty("dateEmbauche") Timestamp dateEmbauche,
            @JsonProperty("admin") boolean admin,
            @JsonProperty("idService") int idService) {
        this.idEmploye = new SimpleIntegerProperty(idEmploye);
        this.nomEmployee = new SimpleStringProperty(nomEmployee);
        this.prenomEmployee = new SimpleStringProperty(prenomEmployee);
        this.posteEmployee = new SimpleStringProperty(posteEmployee);
        this.fixeEmployee = new SimpleStringProperty(fixeEmployee);
        this.mailEmployee = new SimpleStringProperty(mailEmployee);
        this.dateNaissance = new SimpleObjectProperty<>(dateNaissance);
        this.dateEmbauche = new SimpleObjectProperty<>(dateEmbauche);
        this.admin = new SimpleBooleanProperty(admin);
        this.idService = new SimpleIntegerProperty(idService);
    }

    public int getIdEmploye() {
        return idEmploye.get();
    }

    public SimpleIntegerProperty idEmployeProperty() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye.set(idEmploye);
    }

    public String getNomEmploye() {
        return nomEmployee.get();
    }

    public SimpleStringProperty nomEmployeProperty() {
        return nomEmployee;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmployee.set(nomEmploye);
    }

    public String getPrenomEmploye() {
        return prenomEmployee.get();
    }

    public SimpleStringProperty prenomEmployeProperty() {
        return prenomEmployee;
    }

    public void setPrenomEmploye(String prenomEmploye) {
        this.prenomEmployee.set(prenomEmploye);
    }

    public String getPosteEmploye() {
        return posteEmployee.get();
    }

    public SimpleStringProperty posteEmployeProperty() {
        return posteEmployee;
    }

    public void setPosteEmploye(String posteEmploye) {
        this.posteEmployee.set(posteEmploye);
    }

    public String getFixeEmploye() {
        return fixeEmployee.get();
    }

    public SimpleStringProperty fixeEmployeProperty() {
        return fixeEmployee;
    }

    public void setFixeEmploye(String fixeEmploye) {
        this.fixeEmployee.set(fixeEmploye);
    }

    public String getMailEmploye() {
        return mailEmployee.get();
    }

    public SimpleStringProperty mailEmployeProperty() {
        return mailEmployee;
    }

    public void setMailEmploye(String mailEmploye) {
        this.mailEmployee.set(mailEmploye);
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
