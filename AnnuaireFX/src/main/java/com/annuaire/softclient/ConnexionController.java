package com.annuaire.softclient;

import com.annuaire.softclient.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class ConnexionController {

    @FXML
    private Button buttonGoToHomePage;

    @FXML
    private void initialize() {

    }

    @FXML
    private void pressButtonConnexion(ActionEvent event) {
        UserSession userSession = UserSession.getInstance();

        if (!userSession.isAdmin()) {
            System.out.println(UserSession.getInstance().isAdmin());
            goToHomePage();
        }
    }

    @FXML
    void goToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonGoToHomePage.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.isControlDown() && event.isShiftDown()) {
            String adminPassword = "Admin";

            TextInputDialog askPassAdmin = new TextInputDialog();
            askPassAdmin.setTitle("Connexion admin");
            askPassAdmin.setContentText("Mot de passe pour la connexion admin : ");

            askPassAdmin.showAndWait().ifPresent(passwordPrint -> {
                if (passwordPrint.equals(adminPassword)) {
                    UserSession.getInstance().setAdmin(true);
                    System.out.println(UserSession.getInstance().isAdmin());
                    goToHomePage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Le mot de passe entré est incorrect");
                    alert.showAndWait();
                }
            });
        }
    }
}
