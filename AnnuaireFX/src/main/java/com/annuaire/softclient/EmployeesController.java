package com.annuaire.softclient;

import com.annuaire.softclient.dao.EmployeesDAO;
import com.annuaire.softclient.dao.ServicesDAO;
import com.annuaire.softclient.model.Employees;
import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.model.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class EmployeesController {

    @FXML
    private TableColumn<Employees, Timestamp> dateNaissanceEmployeeField;

    @FXML
    private Button buttonAddEmployee;

    @FXML
    private TableColumn<Employees, String> fixeEmployeeField;

    @FXML
    private TableColumn<Employees, String> mailEmployeeField;

    @FXML
    private TableView<Employees> tableauEmployees;

    @FXML
    private TableColumn<Employees, String> prenomEmployeeField;

    @FXML
    private TableColumn<Employees, Timestamp> dateEmbaucheEmployeeField;

    @FXML
    private TableColumn<Employees, Integer> idEmployeeField;

    @FXML
    private TableColumn<Employees, Integer> isAdminEmployeeField;

    @FXML
    private TableColumn<Employees, String> posteEmployeeField;

    @FXML
    private TableColumn<Employees, String> nomEmployeeField;

    @FXML
    private AnchorPane listeEmployees;

    @FXML
    private TableColumn<Employees, Integer> idServiceField;

    private final EmployeesDAO employeesDAO = new EmployeesDAO();
    private final ServicesDAO servicesDAO= new ServicesDAO();

    @FXML
    private void initialize(){
        idEmployeeField.setCellValueFactory(cellData -> cellData.getValue().idEmployeProperty().asObject());
        nomEmployeeField.setCellValueFactory(cellData -> cellData.getValue().nomEmployeProperty());
        prenomEmployeeField.setCellValueFactory(cellData -> cellData.getValue().prenomEmployeProperty());
        posteEmployeeField.setCellValueFactory(cellData -> cellData.getValue().posteEmployeProperty());
        fixeEmployeeField.setCellValueFactory(cellData -> cellData.getValue().fixeEmployeProperty());
        mailEmployeeField.setCellValueFactory(cellData -> cellData.getValue().mailEmployeProperty());
        dateNaissanceEmployeeField.setCellValueFactory(cellData -> cellData.getValue().dateNaissanceProperty());
        dateEmbaucheEmployeeField.setCellValueFactory(cellData -> cellData.getValue().dateEmbaucheProperty());
        isAdminEmployeeField.setCellValueFactory(cellData -> cellData.getValue().idEmployeProperty().asObject());
        idServiceField.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());

        loadDataIntoTable();

        tableauEmployees.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                openDetails();
            }
        });

    }

    private void loadDataIntoTable(){
        List<Employees> listeEmployees = employeesDAO.getAllEmployees();
        ObservableList<Employees> employeesObservableList = FXCollections.observableArrayList(listeEmployees);
        tableauEmployees.setItems(employeesObservableList);
    }

    @FXML
    void addEmployees(ActionEvent event){
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addEmployees.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage modalStage = new Stage();
                modalStage.setScene(scene);
                modalStage.setTitle("Ajouter un employé");

                modalStage.initModality(Modality.APPLICATION_MODAL);

                modalStage.showAndWait();
                loadDataIntoTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            buttonAddEmployee.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("tu n'as pas les droit pour ajouter un employé");
            alert.showAndWait();
            loadDataIntoTable();
        }

    }

    @FXML
    void openDetails() {
        // Récupérer l'objet Employees correspondant à la ligne sélectionnée
        Employees selectedEmployee = tableauEmployees.getSelectionModel().getSelectedItem();

        //On vérifie qu'un élément est sélectionné
        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsEmployees.fxml"));
                Parent root = loader.load();

                // Correction ici : utilisez le contrôleur DetailsEmployeesController
                DetailsEmployeesController controller = loader.getController();

                // Initialiser les données dans le contrôleur des détails des employés
                controller.initData(selectedEmployee);

                //Création de la nouvelle scene
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Détails de l'employé");
                stage.showAndWait();
                loadDataIntoTable();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune ligne sélectionnée !");
            alert.showAndWait();
        }
    }


}
