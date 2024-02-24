package com.annuaire.softclient;

import com.annuaire.softclient.dao.AdressesDAO;
import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.NewAdresses;
import com.annuaire.softclient.model.NewSites;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddSitesController {

    @FXML
    private Button buttonAnnuler;

    @FXML
    private TextField villeSiteField;

    @FXML
    private TextField mailSiteField;

    @FXML
    private TextField nomSiteField;

    @FXML
    private TextField telSiteField;

    @FXML
    private TextField typeSiteField;

    private final SitesDAO sitesDAO;
    private final AdressesDAO adressesDAO;

    public AddSitesController() {
        this.sitesDAO = new SitesDAO();
        this.adressesDAO = new AdressesDAO();
    }


    public void buttonAddSites(ActionEvent actionEvent) {
        try {

            NewSites newSites = new NewSites(nomSiteField.getText(), telSiteField.getText(), mailSiteField.getText(), typeSiteField.getText(), villeSiteField.getText());
            sitesDAO.createSites(newSites);


            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Nouveau site de travail ajouté avec succès !");
            successAlert.showAndWait();
            AnnulerAdd(new ActionEvent());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur Format");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur Exception");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + e.getMessage());
            errorAlert.showAndWait();
        }

    }



    @FXML
    void AnnulerAdd(ActionEvent event){
        Scene scene = buttonAnnuler.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    private void displayErrorAlert(String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

}
