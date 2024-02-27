package com.annuaire.softclient;


import com.annuaire.softclient.dao.EmployeesDAO;
import com.annuaire.softclient.dao.ServicesDAO;
import com.annuaire.softclient.model.NewEmployees;
import com.annuaire.softclient.model.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class AddEmployeesController {

    @FXML
    private Button buttonAnnuler;

    @FXML
    private TextField adminEmployeeField;

    @FXML
    private DatePicker dateNaissanceEmployeeField;

    @FXML
    private DatePicker dateEmbaucheEmployeeField;

    @FXML
    private TextField fixeEmployeeField;

    @FXML
    private TextField mailEmployeeField;

    @FXML
    private TextField nomEmployeeField;

    @FXML
    private TextField posteEmployeeField;

    @FXML
    private TextField prenomEmployeeField;

    @FXML
    private ComboBox<String> employeeComboBox;

    private final EmployeesDAO employeesDAO;
    private final ServicesDAO servicesDAO;

    public AddEmployeesController(){
        this.employeesDAO = new EmployeesDAO();
        this.servicesDAO = new ServicesDAO();
    }

    @FXML
    public void initialize(){
        loadService();
    }

    private void loadService(){
        List<Services> servicesList = servicesDAO.getAllServices();
        for(Services services : servicesList){
            employeeComboBox.getItems().add(services.getNomService());
        }
    }

    @FXML
    void AddEmployees(ActionEvent event) {
        try {
            String nomEmployee = nomEmployeeField.getText();
            String prenomEmployee = prenomEmployeeField.getText();
            String posteEmployee = posteEmployeeField.getText();
            String fixeEmployee = fixeEmployeeField.getText();
            String mailEmployee = mailEmployeeField.getText();
            LocalDate selectedDateNaissance = dateNaissanceEmployeeField.getValue();
            java.sql.Date sqlDateNaissance = java.sql.Date.valueOf(selectedDateNaissance);
            Timestamp dateNaissance = new Timestamp(sqlDateNaissance.getTime());

            LocalDate selectedDateEmbauche = dateEmbaucheEmployeeField.getValue();
            java.sql.Date sqlDateEmbauche = java.sql.Date.valueOf(selectedDateEmbauche);
            Timestamp dateEmbauche = new Timestamp(sqlDateEmbauche.getTime());
            Boolean admin = Boolean.parseBoolean(adminEmployeeField.getText());


            String selectedServiceName = employeeComboBox.getValue();
            int idService = servicesDAO.getServiceIdByName(selectedServiceName);

            // Vérification que les champs de téléphone ne contiennent que des chiffres (max 10)
            if (!isNumeric(fixeEmployee)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez entre un numéro de téléphone ou une adresse mail valide.");
                errorAlert.showAndWait();
                return;
            }

            // Vérification que le champ de l'email contient une adresse email valide
            if (!isValidEmail(mailEmployee)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez entre un numéro de téléphone ou une adresse mail valide.");
                errorAlert.showAndWait();
                return;
            }


            NewEmployees employees = new NewEmployees(nomEmployee, prenomEmployee, idService, posteEmployee, fixeEmployee, mailEmployee, dateNaissance,dateEmbauche, admin);
            employeesDAO.createEmployees(employees);


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

    @FXML
    void AnnulerAdd(ActionEvent event) {
        Stage stage = (Stage) buttonAnnuler.getScene().getWindow();
        stage.close();
    }

    // Vérification que les champs de téléphone ne contiennent que des chiffres (max 10)
    private boolean isNumeric(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Vérification qu'une adresse email est valide
    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier une adresse email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }



}
