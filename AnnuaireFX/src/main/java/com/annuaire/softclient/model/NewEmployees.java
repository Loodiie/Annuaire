package com.annuaire.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class NewEmployees {
    private final SimpleStringProperty nomEmployee;
    private final SimpleStringProperty prenomEmployee;
    private final SimpleIntegerProperty idService;
    private final SimpleStringProperty posteEmployee;
    private final SimpleStringProperty fixeEmployee;
    private final SimpleStringProperty mailEmployee;
    private final SimpleObjectProperty<Timestamp> dateNaissance;
    private final SimpleObjectProperty<Timestamp> dateEmbauche;
    private final SimpleBooleanProperty admin;

    public NewEmployees(
            @JsonProperty("nomEmployee") String nomEmployee,
            @JsonProperty("prenomEmployee") String prenomEmployee,
            @JsonProperty("idService") int idService,
            @JsonProperty("posteEmployee") String posteEmployee,
            @JsonProperty("fixeEmployee") String fixeEmployee,
            @JsonProperty("mailEmployee") String mailEmployee,
            @JsonProperty("dateNaissance") Timestamp dateNaissance,
            @JsonProperty("dateEmbauche") Timestamp dateEmbauche,
            @JsonProperty("admin") boolean admin) {
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

    public String getNomEmployee() {
        return nomEmployee.get();
    }

    public SimpleStringProperty nomEmployeProperty() {
        return nomEmployee;
    }

    public void setNomEmployee(String nomEmploye) {
        this.nomEmployee.set(nomEmploye);
    }

    public String getPrenomEmployee() {
        return prenomEmployee.get();
    }

    public SimpleStringProperty prenomEmployeProperty() {
        return prenomEmployee;
    }

    public void setPrenomEmployee(String prenomEmploye) {
        this.prenomEmployee.set(prenomEmploye);
    }

    public String getPosteEmployee() {
        return posteEmployee.get();
    }

    public SimpleStringProperty posteEmployeProperty() {
        return posteEmployee;
    }

    public void setPosteEmployee(String posteEmploye) {
        this.posteEmployee.set(posteEmploye);
    }

    public String getFixeEmployee() {
        return fixeEmployee.get();
    }

    public SimpleStringProperty fixeEmployeProperty() {
        return fixeEmployee;
    }

    public void setFixeEmployee(String fixeEmploye) {
        this.fixeEmployee.set(fixeEmploye);
    }

    public String getMailEmployee() {
        return mailEmployee.get();
    }

    public SimpleStringProperty mailEmployeProperty() {
        return mailEmployee;
    }

    public void setMailEmployee(String mailEmploye) {
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