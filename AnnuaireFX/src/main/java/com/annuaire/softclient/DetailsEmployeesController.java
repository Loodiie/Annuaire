package com.annuaire.softclient;

import com.annuaire.softclient.dao.EmployeesDAO;
import com.annuaire.softclient.dao.ServicesDAO;
import com.annuaire.softclient.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class DetailsEmployeesController {

    @FXML
    private TextField adminEmployeeField;

    @FXML
    private Button buttonAnnuler;

    @FXML
    private Button buttonDeleteEmploye;

    @FXML
    private Button buttonUpdateEmploye;

    @FXML
    private TextField dateEmbaucheEmployeeField;

    @FXML
    private TextField dateNaissanceEmployeeField;

    @FXML
    private TextField fixeEmployeeField;

    @FXML
    private TextField idEmployeeField;

    @FXML
    private TextField mailEmployeeField;

    @FXML
    private TextField nomEmployeeField;

    @FXML
    private TextField posteEmployeeField;

    @FXML
    private TextField prenomEmployeeField1;

    @FXML
    private TextField idServiceField;

    @FXML
    private ComboBox<String> employeeComboBox;

    private Employees selectedEmployees;
    private final EmployeesDAO employeesDAO = new EmployeesDAO();
    private final ServicesDAO servicesDAO = new ServicesDAO();

    @FXML
    public void initialize() {
        loadService();
    }

    private void loadService() {
        List<Services> servicesList = servicesDAO.getAllServices();
        for (Services services : servicesList) {
            employeeComboBox.getItems().add(services.getNomService());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employees.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            parentStage.setScene(scene);
            parentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            int idEmploye = selectedEmployees.getIdEmploye();

            // Boîte de dialogue pour confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce site ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    employeesDAO.deleteEmployees(idEmploye);

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
            buttonDeleteEmploye.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour supprimer ce site");
            alert.showAndWait();
        }
    }

    @FXML
    void updateEmployee(ActionEvent event) throws ParseException {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            // Boîte de dialogue de confirmation avec un bouton "OK"
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment mettre à jour la fiche salarié ?");

            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == buttonTypeOK) {
            String dateNaissance = dateNaissanceEmployeeField.getText();
            String dateEmbauche = dateEmbaucheEmployeeField.getText();
            SimpleDateFormat dateNaissanceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateEmbaucheFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            java.util.Date parsedDateNaissance = dateNaissanceFormat.parse(dateNaissance);
            Timestamp timestampDateNaissance = new Timestamp(parsedDateNaissance.getTime());

            java.util.Date parsedDateEmbauche = dateEmbaucheFormat.parse(dateEmbauche);
            Timestamp timestampDateEmbauche = new Timestamp(parsedDateEmbauche.getTime());

            // Vérification du format du numéro de téléphone fixe
            String fixeEmployee = fixeEmployeeField.getText();
            if (!isNumeric(fixeEmployee) || fixeEmployee.length() != 10 ) {
                showErrorAlert("Veuillez entre un numéro de téléphone valide.");
                return;
            }

            // Vérification du format de l'e-mail
            String mailemployee = mailEmployeeField.getText();
            if (!isValidEmail(mailemployee)) {
                showErrorAlert("L'adresse e-mail n'est pas valide.");
                return;
            }


            NewEmployees newEmployees = new NewEmployees(
                    nomEmployeeField.getText(),
                    prenomEmployeeField1.getText(),
                    Integer.parseInt(idServiceField.getText()),
                    posteEmployeeField.getText(),
                    fixeEmployeeField.getText(),
                    mailEmployeeField.getText(),
                    timestampDateNaissance,
                    timestampDateEmbauche,
                    Boolean.parseBoolean(adminEmployeeField.getText())
            );

            int employeeId = Integer.parseInt(idEmployeeField.getText());

            try {
                employeesDAO.updateEmployees(employeeId, newEmployees);
                // Afficher une fenêtre pop-up de réussite
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Mise à jour réussie");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'employé a été mis à jour avec succès.");
                successAlert.showAndWait();
                // Fermer la fenêtre actuelle
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException ex) {
                // Gérer les erreurs lors de la mise à jour, par exemple, afficher un message d'erreur
                ex.printStackTrace();
                System.err.println("Erreur lors de la mise à jour du l'employé : " + ex.getMessage());
            }
            }
        } else {
            buttonUpdateEmploye.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour modifier cet employé");
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

    public void initData(Employees employees) {
        selectedEmployees = employees;
        int idService = employees.getIdService ();

        idEmployeeField.setText (String.valueOf (employees.getIdEmploye ()));
        nomEmployeeField.setText (employees.getNomEmploye ());
        prenomEmployeeField1.setText (employees.getPrenomEmploye ());
        posteEmployeeField.setText (employees.getPosteEmploye ());
        fixeEmployeeField.setText (employees.getFixeEmploye ());
        mailEmployeeField.setText (employees.getMailEmploye ());
        dateNaissanceEmployeeField.setText (String.valueOf (employees.getDateNaissance ()));
        dateEmbaucheEmployeeField.setText (String.valueOf (employees.getDateEmbauche ()));
        adminEmployeeField.setText (String.valueOf (employees.isAdmin ()));
        idServiceField.setText (String.valueOf (employees.getIdService ()));

        Services services = servicesDAO.getServicesById (idService);
        if (services != null) {
            idServiceField.setText (String.valueOf (services.getIdService ()));
        } else {
            idEmployeeField.setEditable (false);
            nomEmployeeField.setEditable (false);
            prenomEmployeeField1.setEditable (false);
            idServiceField.setEditable (false);
            posteEmployeeField.setEditable (false);
            fixeEmployeeField.setEditable (false);
            mailEmployeeField.setEditable (false);
            dateNaissanceEmployeeField.setEditable (false);
            dateEmbaucheEmployeeField.setEditable (false);
            adminEmployeeField.setEditable (false);
        }
    }
}




