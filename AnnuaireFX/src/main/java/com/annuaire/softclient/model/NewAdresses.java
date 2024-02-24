package com.annuaire.softclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NewAdresses {
    private final SimpleStringProperty nomRue;
    private final SimpleStringProperty nomBatiment;
    private final SimpleIntegerProperty numRue;
    private final SimpleStringProperty complement;
    private final SimpleStringProperty codePostal;
    private final SimpleStringProperty ville;

    public NewAdresses(
            @JsonProperty("nomRue") String nomRue,
            @JsonProperty("nomBatiment") String nomBatiment,
            @JsonProperty("numRue") Integer numRue,
            @JsonProperty("complement") String complement,
            @JsonProperty("codePostal") String codePostal,
            @JsonProperty("cityName") String ville) {
        this.nomRue = new SimpleStringProperty(nomRue);
        this.nomBatiment = new SimpleStringProperty(nomBatiment);
        this.numRue = new SimpleIntegerProperty(numRue);
        this.complement = new SimpleStringProperty(complement);
        this.codePostal = new SimpleStringProperty(codePostal);
        this.ville = new SimpleStringProperty(ville);
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

    public SimpleIntegerProperty lineNumRueProperty() {
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
