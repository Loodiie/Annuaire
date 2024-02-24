package com.annuaire.softclient;
import com.annuaire.softclient.dao.AdressesDAO;
import com.annuaire.softclient.model.Adresses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;
public class InfoAdressesController {

    @FXML
    private TableView<Adresses> adressesTableView;

    @FXML
    private TableColumn<Adresses, String> nomRue;

    @FXML
    private TableColumn<Adresses, String> nomBatiment;

    @FXML
    private Button buttonAnnuler;

    @FXML
    private TableColumn<Adresses, String> ville;

    @FXML
    private TableColumn<Adresses, String> codePostal;

    @FXML
    private TableColumn<Adresses, Integer> idAdresse;

    @FXML
    private TableColumn<Adresses, Integer> numRue;

    @FXML
    private TableColumn<Adresses, String> complement;

    private final AdressesDAO adressesDAO = new AdressesDAO();

    @FXML
    void annulerUp(ActionEvent event) {
        Stage popupStage = (Stage) buttonAnnuler.getScene().getWindow();
        popupStage.close();
    }

    @FXML
    public void initializeAdressesTable() {
        idAdresse.setCellValueFactory(cellData -> cellData.getValue().idAdresseProperty().asObject());
        nomRue.setCellValueFactory(cellData -> cellData.getValue().nomRueProperty());
        nomBatiment.setCellValueFactory(cellData -> cellData.getValue().nomBatimentProperty());
        numRue.setCellValueFactory(cellData -> cellData.getValue().NumRueProperty().asObject());
        complement.setCellValueFactory(cellData -> cellData.getValue().complementProperty());
        codePostal.setCellValueFactory(cellData -> cellData.getValue().codePostalProperty());
        ville.setCellValueFactory(cellData -> cellData.getValue().villeProperty());

        loadDataIntoTable();
    }

    private void loadDataIntoTable() {
        List<Adresses> adressesList = adressesDAO.getAllAdresses(); //Liste des sites de travail depuis l'API
        ObservableList<Adresses> addressesObservableList = FXCollections.observableArrayList(adressesList); //Crée la liste observable à partir de la liste pour la liaison
        adressesTableView.setItems(addressesObservableList);
    }

}
