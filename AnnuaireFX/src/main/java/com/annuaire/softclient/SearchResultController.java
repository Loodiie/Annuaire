package com.annuaire.softclient;

import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.Sites;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;


public class SearchResultController {
    @FXML
    private TableView<Sites> sitesTableView;

    @FXML
    private TableColumn<Sites, String> villeSite;

    @FXML
    private TableColumn<Sites, String> mailSite;

    @FXML
    private TableColumn<Sites, Integer> idSite;

    @FXML
    private TableColumn<Sites, String>  nomSite;

    @FXML
    private TableColumn<Sites, String>  telSite;

    @FXML
    private TableColumn<Sites, String>  typeSite;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField searchField;

    private final SitesDAO sitesDAO = new SitesDAO();

    @FXML
    public void initialize() {
        idSite.setCellValueFactory(cellData -> cellData.getValue().idSiteProperty().asObject());
        nomSite.setCellValueFactory(cellData -> cellData.getValue().nomSiteProperty());
        telSite.setCellValueFactory(cellData -> cellData.getValue().telSiteProperty());
        mailSite.setCellValueFactory(cellData -> cellData.getValue().mailSiteProperty());
        typeSite.setCellValueFactory(cellData -> cellData.getValue().typeSiteProperty());
        villeSite.setCellValueFactory(cellData -> cellData.getValue().villeSiteProperty());

        buttonSearch.setOnAction(event -> makeAResearch());

        //TODO : mettre l'écouteur d'évènement pour l'affichage des détails
    }


    public void makeAResearch() {
        String searchTerm = searchField.getText();
        List<Sites> searchResults = sitesDAO.searchSitesByName(searchTerm);
        updateTable(searchResults);
    }

    private void updateTable(List<Sites> searchResults) {
        ObservableList<Sites> worksiteObservableList = FXCollections.observableArrayList(searchResults);
        sitesTableView.setItems(worksiteObservableList);
    }
}
