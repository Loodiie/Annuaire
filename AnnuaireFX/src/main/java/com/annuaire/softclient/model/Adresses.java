package com.annuaire.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Adresses {
    private final SimpleIntegerProperty idAdresse;
    private final SimpleStringProperty nomRue;
    private final SimpleStringProperty nomBatiment;
    private final SimpleIntegerProperty numRue;
    private final SimpleStringProperty complement;
    private final SimpleStringProperty codePostal;
    private final SimpleStringProperty ville;
    public Adresses(
            @JsonProperty("idAdresse") int idAdresse,
            @JsonProperty("nomRue") String nomRue,
            @JsonProperty("nomBatiment") String nomBatiment,
            @JsonProperty("numRue") Integer numRue,
            @JsonProperty("complement") String complement,
            @JsonProperty("codePostal") String codePostal,
            @JsonProperty("ville") String ville) {
        this.idAdresse = new SimpleIntegerProperty(idAdresse);
        this.nomRue = new SimpleStringProperty(nomRue);
        this.nomBatiment = new SimpleStringProperty(nomBatiment);
        this.numRue = new SimpleIntegerProperty(numRue);
        this.complement = new SimpleStringProperty(complement);
        this.codePostal = new SimpleStringProperty(codePostal);
        this.ville = new SimpleStringProperty(ville);
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

    public String getNomRue() {
        return nomRue.get();
    }

    public SimpleStringProperty nomRueProperty() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue.set(nomRue);
    }

    public String getNomBatiment() {
        return nomBatiment.get();
    }

    public SimpleStringProperty nomBatimentProperty() {
        return nomBatiment;
    }

    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment.set(nomBatiment);
    }

    public int getNumRue() {
        return numRue.get();
    }

    public SimpleIntegerProperty NumRueProperty() {
        return numRue;
    }

    public void setNumRue(int numRue) {
        this.numRue.set(numRue);
    }

    public String getComplement() {return complement.get();}

    public SimpleStringProperty complementProperty() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement.set(complement);
    }

    public String getCodePostal() {
        return codePostal.get();
    }

    public SimpleStringProperty codePostalProperty() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal.set(codePostal);
    }

    public String getVille() {
        return ville.get();
    }

    public SimpleStringProperty villeProperty() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }
}
