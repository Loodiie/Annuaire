package com.annuaire.softclient;

import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.NewSites;
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

    public AddSitesController() {
        this.sitesDAO = new SitesDAO();
    }


    public void buttonAddSites(ActionEvent actionEvent) {
        try {
            String nomSite = nomSiteField.getText();
            String telSite = telSiteField.getText();
            String mailSite = mailSiteField.getText();
            String typeSite = typeSiteField.getText();
            String villeSite = villeSiteField.getText();

            if (!isNumeric(telSite)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez entre un numéro de téléphone ou une adresse mail valide.");
                errorAlert.showAndWait();
                return;
            }

            // Vérification que le champ de l'email contient une adresse email valide
            if (!isValidEmail(mailSite)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez entre un numéro de téléphone ou une adresse mail valide.");
                errorAlert.showAndWait();
                return;
            }

            NewSites newSites = new NewSites(nomSite, telSite, mailSite, typeSite, villeSite);
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
            errorAlert.setTitle("Erreur Exception Site");
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

    private boolean isNumeric(String telSite) {
        return telSite != null && telSite.matches("\\d+") && telSite.length() == 10;
    }

    // Vérification qu'une adresse email est valide
    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier une adresse email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

}
