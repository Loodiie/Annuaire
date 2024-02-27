package com.annuaire.softclient;

import com.annuaire.softclient.model.Sites;
import com.annuaire.softclient.dao.ServicesDAO;
import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.*;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;
import javafx.animation.KeyFrame;


public class DetailsSitesController {


    @FXML
    private Button buttonAnnuler;

    @FXML
    private Button buttonDeleteSites;

    @FXML
    private Button buttonUpdateSites;

    @FXML
    private TextField idSiteField;

    @FXML
    private TableView<Services> tableauServices;

    @FXML
    private TableColumn<Services, String> mailServiceField;

    @FXML
    private TextField mailSiteField;

    @FXML
    private TableColumn<Services, String> nomServiceField;

    @FXML
    private TextField nomSiteField;

    @FXML
    private TableColumn<Services, String> telServiceField;

    @FXML
    private TextField telSiteField;

    @FXML
    private TableColumn<Services, String> typeServiceField;

    @FXML
    private TextField typeSiteField;

    @FXML
    private TextField villeSiteField;

    private Sites selectedSites;

    private final SitesDAO sitesDAO = new SitesDAO();
    private final ServicesDAO servicesDAO = new ServicesDAO();


    @FXML
    private void initialize() {
        //Initialisation des colonnes du tableau
        nomServiceField.setCellValueFactory(cellData -> cellData.getValue().nomServiceProperty());
        typeServiceField.setCellValueFactory(cellData -> cellData.getValue().typeServiceProperty());
        mailServiceField.setCellValueFactory(cellData -> cellData.getValue().mailServiceProperty());
        telServiceField.setCellValueFactory(cellData -> cellData.getValue().telServiceProperty());


    }

    public void loadDataIntoTableService() {

        int idSite = selectedSites.getIdSite();
        if(idSite != 0) {
            List<Services> listesServices = servicesDAO.getAllServicesBySites(idSite);
            ObservableList<Services> servicesObservableList = FXCollections.observableArrayList(listesServices);
            tableauServices.setItems(servicesObservableList);
        }else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("L'id du site n'a pas été trouvé");

        }
    }

    @FXML
    void annulerDetail(ActionEvent event) {
        Stage popupStage = (Stage) buttonAnnuler.getScene().getWindow();
        popupStage.close();

        Stage parentStage = (Stage) popupStage.getOwner();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> reloadSitesPage(parentStage)));
        timeline.play();
    }

    private void reloadSitesPage(Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sites.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            parentStage.setScene(scene);
            parentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteSites(ActionEvent event) throws IOException {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            int idSite = selectedSites.getIdSite();

            // Boîte de dialogue pour confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce site ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    sitesDAO.deleteSites(idSite);


                    // Afficher une alerte de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Le chantier a été supprimé avec succès.");
                    successAlert.showAndWait();

                    // Fermer la fenêtre actuelle
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    // En cas d'erreur lors de la suppression, afficher une alerte d'erreur
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur lors de la suppression");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Erreur lors de la suppression du chantier : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }

        } else {
            buttonDeleteSites.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour supprimer ce site");
            alert.showAndWait();
        }
    }

    @FXML
    void updateSites(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment mettre à jour la fiche salarié ?");

            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == buttonTypeOK) {

                int siteId = Integer.parseInt (idSiteField.getText ());
                String nom = nomSiteField.getText ();
                String type = typeSiteField.getText ();
                String telephone = telSiteField.getText ();
                String email = mailSiteField.getText ();
                String ville = villeSiteField.getText ();

                try {
                    // Vérification du format du numéro de téléphone fixe
                    if (!isNumeric(telSiteField.getText())) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Immobilis !");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Veuillez entre un numéro de téléphone valide.");
                        errorAlert.showAndWait();
                        return;
                    }

                    // Vérification du format de l'e-mail
                    if (!isValidEmail(mailSiteField.getText())) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("L'adresse email n'est pas valide.");
                        errorAlert.showAndWait();
                        return;
                    }
                    // Créer un objet NewSites avec les nouvelles données
                    NewSites newSites = new NewSites (nom, telephone, email, type, ville);
                    // Appeler la méthode update de votre API avec les nouvelles données
                    sitesDAO.updateSites (siteId, newSites);
                    Alert successAlert = new Alert (Alert.AlertType.INFORMATION);
                    successAlert.setTitle ("Mise à jour réussie");
                    successAlert.setHeaderText (null);
                    successAlert.setContentText ("Le site a été mis à jour avec succès.");
                    successAlert.showAndWait ();
                    // Fermer la fenêtre actuelle
                    Stage currentStage = (Stage) ((Node) event.getSource ()).getScene ().getWindow ();
                    currentStage.close ();
                } catch (IOException ex) {
                    // Gérer les erreurs lors de la mise à jour, par exemple, afficher un message d'erreur
                    ex.printStackTrace ();
                    System.err.println ("Erreur lors de la mise à jour du site de travail : " + ex.getMessage ());
                }
            }
        } else {
            buttonUpdateSites.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour modifier le site");
            alert.showAndWait();
        }
    }
    private boolean isNumeric(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier une adresse email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public void initData(Sites sites) {
        selectedSites = sites;

        idSiteField.setText(String.valueOf(sites.getIdSite()));
        nomSiteField.setText(sites.getNomSite());
        telSiteField.setText(sites.getTelSite());
        mailSiteField.setText(sites.getMailSite());
        typeSiteField.setText(sites.getTypeSite());
        villeSiteField.setText(sites.getVilleSite());

    }
}



