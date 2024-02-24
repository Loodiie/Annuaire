package com.annuaire.softclient;
import com.annuaire.softclient.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HeaderController {

    @FXML
    private Button buttonDeconnexion;

    @FXML
    private Button buttonGoToEmployees;

    @FXML
    private Button buttonGoToServices;

    @FXML
    private Button buttonGoToSites;

    @FXML
    private Button buttonSearchResults;

    @FXML
    void disconnectUser(ActionEvent event){
        try{
            UserSession.resetInstance();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("connexion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonDeconnexion.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToEmployees(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employees.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToEmployees.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToServices(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("services.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToServices.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToSites(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sites.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToSites.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchResults(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchResults.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonSearchResults.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}