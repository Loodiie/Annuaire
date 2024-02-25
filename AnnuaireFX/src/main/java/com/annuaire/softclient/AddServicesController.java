package com.annuaire.softclient;

import com.annuaire.softclient.dao.ServicesDAO;
import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.NewServices;
import com.annuaire.softclient.model.Sites;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class AddServicesController {

    @FXML
    private Button buttonAnnuler;
    @FXML
    private DatePicker dateServiceField;
    @FXML
    private TextField mailServiceField;
    @FXML
    private TextField nomServiceField;
    @FXML
    private TextField telServiceField;
    @FXML
    private TextField typeServicesField;
    @FXML
    private ComboBox<String> siteComboBox;

    private final ServicesDAO servicesDAO;
    private final SitesDAO sitesDAO;

    public AddServicesController() {
        this.servicesDAO = new ServicesDAO();
        this.sitesDAO = new SitesDAO();
    }

    @FXML
    public void initialize() {
        // Charge la liste des sites existants dans le ComboBox lors de l'initialisation
        loadSites();
    }

    private void loadSites() {
        List<Sites> sitesList = sitesDAO.getAllSites();
        for (Sites site : sitesList) {
            siteComboBox.getItems().add(site.getNomSite());
        }
    }

    @FXML
    void AnnulerAdd(ActionEvent event) {
        Stage stage = (Stage) buttonAnnuler.getScene().getWindow();
        stage.close();
    }

    @FXML
    void AddServices(ActionEvent event) {
        try {
            String nomService = nomServiceField.getText();
            String typeService = typeServicesField.getText();
            String mailService = mailServiceField.getText();
            String telService = telServiceField.getText();

            LocalDate selectedDate = dateServiceField.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
            Timestamp dateCreation = new Timestamp(sqlDate.getTime());

            String selectedSiteName = siteComboBox.getValue();
            int idSite = sitesDAO.getSiteIdByName(selectedSiteName);

            NewServices services = new NewServices(nomService, typeService, mailService, telService, dateCreation, idSite);
            servicesDAO.createServices(services);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Nouveau service ajouté avec succès !");
            successAlert.showAndWait();
            AnnulerAdd(new ActionEvent());
        } catch (NumberFormatException e) {
            showErrorAlert("Erreur Format", "Erreur lors de l'ajout du service : " + e.getMessage());
        } catch (IOException e) {
            showErrorAlert("Erreur Exception services", "Erreur lors de l'ajout du service : " + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
