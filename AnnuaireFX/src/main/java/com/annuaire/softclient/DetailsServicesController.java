package com.annuaire.softclient;

import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.dao.EmployeesDAO;
import com.annuaire.softclient.model.Employees;
import com.annuaire.softclient.dao.ServicesDAO;
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import javafx.animation.KeyFrame;

public class DetailsServicesController {

    @FXML
    private TableColumn<Employees, String> posteEmployeeField;

    @FXML
    private Button buttonAnnuler;

    @FXML
    private Button buttonDeleteService;

    @FXML
    private Button buttonUpdateSiervice;

    @FXML
    private TextField idServiceField;

    @FXML
    private TextField dateServiceField;

    @FXML
    private TableColumn<Employees, String> mailEmployeeField;

    @FXML
    private TextField mailServiceField;

    @FXML
    private TableColumn<Employees, String> nomEmployeField;

    @FXML
    private TextField nomServiceField;

    @FXML
    private TableColumn<Employees, String> prenomEmployeField;

    @FXML
    private TableView<Employees> tableauEmployees;

    @FXML
    private TableColumn<Employees, String> telEmployeeField;

    @FXML
    private TextField telServiceField;

    @FXML
    private TextField typeServiceField;

    private Services selectedServices;

    private Employees selectedEmployees;

    private final ServicesDAO servicesDAO = new ServicesDAO();
    private final EmployeesDAO employeesDAO = new EmployeesDAO();
    private final SitesDAO sitesDAO = new SitesDAO();

    public void initData(Services services){
        selectedServices = services;
        int idSite = services.getIdSite();

        idServiceField.setText(String.valueOf(services.getIdService()));
        nomServiceField.setText(services.getNomService());
        typeServiceField.setText(services.getTypeService());
        mailServiceField.setText(services.getMailService());
        telServiceField.setText(services.getTelService());
        dateServiceField.setText(String.valueOf(services.getDateCreation()));

    }

    @FXML
    private void initialize() {
        //Initialisation des colonnes du tableau
        nomEmployeField.setCellValueFactory(cellData -> cellData.getValue().nomEmployeProperty());
        prenomEmployeField.setCellValueFactory(cellData -> cellData.getValue().prenomEmployeProperty());
        posteEmployeeField.setCellValueFactory(cellData -> cellData.getValue().posteEmployeProperty());
        telEmployeeField.setCellValueFactory(cellData -> cellData.getValue().fixeEmployeProperty());
        mailEmployeeField.setCellValueFactory(cellData -> cellData.getValue().mailEmployeProperty());

    }

    public void loadDataIntoTableEmployees() {
        if (selectedServices != null) {
            int idService = selectedServices.getIdService();
            if (idService != 0) {
                List<Employees> listeEmployees = employeesDAO.getAllEmployeesByServices(idService);
                ObservableList<Employees> employeesObservableList = FXCollections.observableArrayList(listeEmployees);
                tableauEmployees.setItems(employeesObservableList);
            } else {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("L'id du site n'a pas été trouvé");
            }
        } else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Aucun service sélectionné");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("services.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            parentStage.setScene(scene);
            parentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteService(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            int idService = selectedServices.getIdService();

            // Boîte de dialogue pour confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce site ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    servicesDAO.deleteServices(idService);

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
            buttonDeleteService.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour supprimer ce site");
            alert.showAndWait();
        }
    }


    @FXML
    void updateService(ActionEvent event) throws ParseException {
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

                // Vérification du format du numéro de téléphone fixe et du numéro de téléphone portable
                String telServices = telServiceField.getText();
                if (!isNumeric(telServices) || telServices.length() != 10 ) {
                    showErrorAlert("Veuillez entre un numéro de téléphone valide.");
                    return;
                }

                // Vérification du format de l'e-mail
                String mailServices = mailServiceField.getText();
                if (!isValidEmail(mailServices)) {
                    showErrorAlert("L'adresse e-mail n'est pas valide.");
                    return;
                }

                String dateString = dateServiceField.getText ();
                SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

                // On converti le texte en Timestamp
                java.util.Date parsedDate = dateFormat.parse (dateString);
                Timestamp timestampDateCreation = new Timestamp (parsedDate.getTime ());

                // On rappelle timestamp pour créer le nouvel objet
                NewServices newServices = new NewServices (
                        nomServiceField.getText (),
                        typeServiceField.getText (),
                        mailServiceField.getText (),
                        telServiceField.getText (),
                        timestampDateCreation,
                        Integer.parseInt(idServiceField.getText())
                );

                int idService = Integer.parseInt (idServiceField.getText ());

                try {
                    servicesDAO.updateServices (idService, newServices);
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
            buttonUpdateSiervice.setDisable(true);
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

}
