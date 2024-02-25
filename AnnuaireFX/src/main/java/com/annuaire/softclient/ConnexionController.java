package com.annuaire.softclient;

import com.annuaire.softclient.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;


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

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Mot de passe");

            Dialog<String> passwordDialog = new Dialog<>();
            passwordDialog.setTitle("Connexion admin");
            passwordDialog.setHeaderText(null);
            passwordDialog.getDialogPane().setContent(passwordField);
            passwordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Stage dialogStage = (Stage) passwordDialog.getDialogPane().getScene().getWindow();

            try {
                InputStream iconStream = getClass().getClassLoader().getResourceAsStream("assets/icons/icon.png");
                if (iconStream != null) {
                    dialogStage.getIcons().add(new Image(iconStream));
                } else {
                    System.out.println("Icon not found");
                }
            } catch (NullPointerException e) {
                System.out.println("Error loading icon: " + e.getMessage());
            }

            passwordDialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return passwordField.getText();
                }
                return null;
            });

            passwordDialog.showAndWait().ifPresent(passwordPrint -> {
                if (passwordPrint != null && passwordPrint.equals(adminPassword)) {
                    UserSession.getInstance().setAdmin(true);
                    System.out.println(UserSession.getInstance().isAdmin());
                    goToHomePage();
                } else {
                    Label errorMessage = new Label("Le mot de passe entré est incorrect");
                    errorMessage.setStyle("-fx-text-fill: red;");
                    passwordDialog.getDialogPane().setContent(errorMessage);
                    passwordDialog.showAndWait();
                }
            });
        }
    }
}

