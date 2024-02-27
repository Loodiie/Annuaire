package com.annuaire.softclient;

import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.dao.ServicesDAO;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class ServicesController {
    @FXML
    private Button buttonAddService;
    @FXML
    private TableView<Services> tableauServices;
    @FXML
    private TableColumn<Services, String> nomServiceField;
    @FXML
    private TableColumn<Services, String> telServiceField;
    @FXML
    private TableColumn<Services, String> mailServiceField;
    @FXML
    private TableColumn<Services, String> typeServiceField;
    @FXML
    private TableColumn<Services, Timestamp> dateServiceField;
    @FXML
    private TableColumn<Services, Integer> idServiceField;
    @FXML
    private TableColumn<Services, Integer> idSiteField;
    private final ServicesDAO servicesDAO = new ServicesDAO();

    @FXML
    private void initialize() {
        //Initialisation des colonnes du tableau
        idServiceField.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());
        nomServiceField.setCellValueFactory(cellData -> cellData.getValue().nomServiceProperty());
        typeServiceField.setCellValueFactory(cellData -> cellData.getValue().typeServiceProperty());
        mailServiceField.setCellValueFactory(cellData -> cellData.getValue().mailServiceProperty());
        telServiceField.setCellValueFactory(cellData -> cellData.getValue().telServiceProperty());
        dateServiceField.setCellValueFactory(cellData -> cellData.getValue().dateCreationProperty());
        idSiteField.setCellValueFactory(cellData -> cellData.getValue().idSiteProperty().asObject());

        //Charge les données dans la table
        loadDataIntoTable();

        //Ecouteur évènement
        tableauServices.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openDetails();
            }
        });

    }

    private void loadDataIntoTable() {
        List<Services> listeServices = servicesDAO.getAllServices(); //Liste des services depuis l'API
        ObservableList<Services> servicesObservableList = FXCollections.observableArrayList(listeServices);
        tableauServices.setItems(servicesObservableList);
    }

    @FXML
    void addServices(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addServices.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage modalStage = new Stage();
                modalStage.setScene(scene);
                modalStage.setTitle("Ajouter un service");

                modalStage.initModality(Modality.APPLICATION_MODAL);

                modalStage.showAndWait();
                loadDataIntoTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            buttonAddService.setDisable(true);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour ajouter un service");
            alert.showAndWait();
        }
    }

    @FXML
    void openDetails() {
        // Récupérer l'objet Worksite correspondant à la ligne sélectionnée
        Services selectedServices = tableauServices.getSelectionModel().getSelectedItem();

        //On vérifie qu'un élément est sélectionné
        if (selectedServices != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsServices.fxml"));
                Parent root = loader.load();

                DetailsServicesController controller = loader.getController();

                controller.initData(selectedServices);
                controller.loadDataIntoTableEmployees();

                //Création de la nouvelle scene
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Détails du Sites");
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

