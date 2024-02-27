package com.annuaire.softclient;

import com.annuaire.softclient.dao.EmployeesDAO;
import com.annuaire.softclient.dao.ServicesDAO;
import com.annuaire.softclient.dao.SitesDAO;
import com.annuaire.softclient.model.Employees;
import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.model.Sites;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.util.List;

public class SearchController {
    @FXML
    private TableView<Sites> tableauSite;

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
    private TableColumn<Sites, String> villeSite;
    @FXML
    private Tab resultsSite;

    @FXML
    private TableView<Services> tableauService;


    @FXML
    private TableColumn<Services, Timestamp> dateService;

    @FXML
    private TableColumn<Services, String> mailService;

    @FXML
    private TableColumn<Services, Integer> idService;

    @FXML
    private TableColumn<Services, String> nomService;

    @FXML
    private TableColumn<Services, String> telService;

    @FXML
    private TableColumn<Services, String> typeService;

    @FXML
    private TableColumn<Services, Integer> idSiteService;

    @FXML
    private TableView<Employees> tableauEmployees;

    @FXML
    private TableColumn<Employees, Boolean> adminEmployee;

    @FXML
    private TableColumn<Employees, Timestamp> dateNaissanceEmployee;

    @FXML
    private TableColumn<Employees, String> fixeEmployee;

    @FXML
    private TableColumn<Employees, String> mailEmployee;

    @FXML
    private TableColumn<Employees, String> prenomEmployee;

    @FXML
    private TableColumn<Employees, Timestamp> dateEmbaucheEmployee;

    @FXML
    private TableColumn<Employees, Integer> idEmploye;

    @FXML
    private TableColumn<Employees, Integer> idServiceEmployee;

    @FXML
    private TableColumn<Employees, String> posteEmployee;

    @FXML
    private TableColumn<Employees, String> nomEmployee;

    @FXML
    private TableColumn<Employees, String> nomServiceEmployee;


    @FXML
    private Button buttonSearch;

    @FXML
    private TextField searchField;

    private final SitesDAO sitesDAO  = new SitesDAO();
    private final ServicesDAO servicesDAO = new ServicesDAO();
    private final EmployeesDAO employeesDAO = new EmployeesDAO();

    @FXML
    public void initialize() {
        idSite.setCellValueFactory(cellData -> cellData.getValue().idSiteProperty().asObject());
        nomSite.setCellValueFactory(cellData -> cellData.getValue().nomSiteProperty());
        telSite.setCellValueFactory(cellData -> cellData.getValue().telSiteProperty());
        mailSite.setCellValueFactory(cellData -> cellData.getValue().mailSiteProperty());
        typeSite.setCellValueFactory(cellData -> cellData.getValue().typeSiteProperty());
        villeSite.setCellValueFactory(cellData -> cellData.getValue().villeSiteProperty());

        idService.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());
        nomService.setCellValueFactory(cellData -> cellData.getValue().nomServiceProperty());
        typeService.setCellValueFactory(cellData -> cellData.getValue().typeServiceProperty());
        mailService.setCellValueFactory(cellData -> cellData.getValue().mailServiceProperty());
        telService.setCellValueFactory(cellData -> cellData.getValue().telServiceProperty());
        dateService.setCellValueFactory(cellData -> cellData.getValue().dateCreationProperty());
        idSiteService.setCellValueFactory(cellData -> cellData.getValue().idSiteProperty().asObject());

        idEmploye.setCellValueFactory(cellData -> cellData.getValue().idEmployeProperty().asObject());
        nomEmployee.setCellValueFactory(cellData -> cellData.getValue().nomEmployeProperty());
        prenomEmployee.setCellValueFactory(cellData -> cellData.getValue().prenomEmployeProperty());
        posteEmployee.setCellValueFactory(cellData -> cellData.getValue().posteEmployeProperty());
        fixeEmployee.setCellValueFactory(cellData -> cellData.getValue().fixeEmployeProperty());
        mailEmployee.setCellValueFactory(cellData -> cellData.getValue().mailEmployeProperty());
        dateNaissanceEmployee.setCellValueFactory(cellData -> cellData.getValue().dateNaissanceProperty());
        dateEmbaucheEmployee.setCellValueFactory(cellData -> cellData.getValue().dateEmbaucheProperty());
        idServiceEmployee.setCellValueFactory(cellData -> cellData.getValue().idServiceProperty().asObject());
        nomServiceEmployee.setCellValueFactory(cellData -> {
            int serviceId = cellData.getValue().getIdService();
            Services service = servicesDAO.getServicesById(serviceId);
            if (service != null) {
                System.out.println("Service récupéré : " + service.getNomService());
                return service.nomServiceProperty();
            } else {
                System.out.println("Aucun service trouvé pour l'ID : " + serviceId);
                return new SimpleStringProperty("Service Inconnu");
            }
        });

        buttonSearch.setOnAction(event -> makeAResearch());

    }


    public void makeAResearch() {
        String searchTerm = searchField.getText();
        List<Sites> searchResultsSites = sitesDAO.searchSitesByName(searchTerm);
        System.out.println("sites : " + searchTerm);
        updateTableSites(searchResultsSites);
        List<Services> searchResultServices = servicesDAO.searchServiceSiteByName(searchTerm);
        System.out.println("services : " + searchTerm);
        updateTableServices(searchResultServices);
        List<Employees> searchResultEmployees = employeesDAO.searchServiceByName(searchTerm);
        System.out.println("employees : " + searchTerm);
        updateTableEmployees(searchResultEmployees);

    }

    private void updateTableSites(List<Sites> searchResults) {
        ObservableList<Sites> sitesObservableList = FXCollections.observableArrayList(searchResults);
        tableauSite.setItems(sitesObservableList);
    }
    private void updateTableServices(List<Services> searchResults) {
        ObservableList<Services> servicesObservableList = FXCollections.observableArrayList(searchResults);
        tableauService.setItems(servicesObservableList);
    }
    private void updateTableEmployees(List<Employees> searchResults) {
        ObservableList<Employees> employeesObservableList = FXCollections.observableArrayList(searchResults);
        tableauEmployees.setItems(employeesObservableList);
    }

}
