package com.annuaire.softclient;

import com.annuaire.softclient.model.Sites;
import com.annuaire.softclient.dao.SitesDAO;
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
import java.util.List;

public class SitesController {
    @FXML
    private Button buttonAddSites;
    @FXML
    private Button buttonReload;
    @FXML
    private TableView<Sites> tableauSites;
    @FXML
    private TableColumn<Sites, String> nomSite;
    @FXML
    private TableColumn<Sites, String> telSite;
    @FXML
    private TableColumn<Sites, String> mailSite;
    @FXML
    private TableColumn<Sites, String> typeSite;
    @FXML
    private TableColumn<Sites, String> villeSite;
    @FXML
    private TableColumn<Sites, Integer> idSite;
    private final SitesDAO sitesDAO = new SitesDAO();

    @FXML
    private void initialize() {
        //Initialisation des colonnes du tableau
        idSite.setCellValueFactory(cellData -> cellData.getValue().idSiteProperty().asObject());
        nomSite.setCellValueFactory(cellData -> cellData.getValue().nomSiteProperty());
        telSite.setCellValueFactory(cellData -> cellData.getValue().telSiteProperty());
        mailSite.setCellValueFactory(cellData -> cellData.getValue().mailSiteProperty());
        typeSite.setCellValueFactory(cellData -> cellData.getValue().typeSiteProperty());
        villeSite.setCellValueFactory(cellData -> cellData.getValue().villeSiteProperty());

        //Charge les données dans la table
        loadDataIntoTable();

        //Ecouteur évènement
        tableauSites.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openDetails();
            }
        });

        buttonReload.setOnAction(event -> reloadTableau());
    }

    @FXML
    private void reloadTableau(){
        loadDataIntoTable();
    }

    private void loadDataIntoTable() {
        List<Sites> listeSites = sitesDAO.getAllSites(); //Liste des sites de travail depuis l'API
        ObservableList<Sites> sitesObservableList = FXCollections.observableArrayList(listeSites);
        tableauSites.setItems(sitesObservableList);
    }

    @FXML
    void addSites(ActionEvent event) {
        boolean isAdmin = UserSession.getInstance().isAdmin();
        if (isAdmin) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addSites.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage modalStage = new Stage();
                modalStage.setScene(scene);
                modalStage.setTitle("Ajouter un site de travail");

                modalStage.initModality(Modality.APPLICATION_MODAL);

                modalStage.showAndWait();
                loadDataIntoTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            buttonAddSites.setDisable(true);
            alert.setTitle("Lashlabask");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas les droits pour ajouter un site");
            alert.showAndWait();
        }
    }

    @FXML
    void openDetails() {
        // Récupérer l'objet Worksite correspondant à la ligne sélectionnée
        Sites selectedSites = tableauSites.getSelectionModel().getSelectedItem();

        //On vérifie qu'un élément est sélectionné
        if (selectedSites != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsSites.fxml"));
                Parent root = loader.load();

                DetailsSitesController controller = loader.getController();

                controller.initData(selectedSites);
                controller.loadDataIntoTableService();

                //Création de la nouvelle scene
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Détails du Sites");
                stage.show();
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

