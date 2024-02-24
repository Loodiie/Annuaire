package com.annuaire.softclient.dao;

import com.annuaire.softclient.model.NewAdresses;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.annuaire.softclient.model.Adresses;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class AdressesDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/adresses";

    public int createAdresses(NewAdresses adresses) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonSite = objectMapper.writeValueAsString(adresses);
            byte[] input = jsonSite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }

        // Lire la réponse JSON
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur Ta mère");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout du site de travail : " + response);
            // Analyser la réponse JSON
            JsonNode jsonResponse = objectMapper.readTree(response.toString());
            JsonNode idNode = jsonResponse.get("id");
            if (idNode != null && idNode.isInt()) {
                int idAdresse = idNode.asInt();
                // Retourner l'ID de l'adresse créée
                return idAdresse;
            } else {
                throw new IOException(response.toString());
            }
        }
    }

    public Adresses getAdressesById(int idAdresse) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/" + idAdresse);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }

                in.close();
            } else {
                responseString.append("Erreur de réponse de l'API. Code : ").append(responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel de l'API : ").append(e.getMessage());
        }
        Adresses responseAdresses = parseJsonString(responseString.toString());
        return responseAdresses;
    }

    public static Adresses parseJsonString(String jsonString) {
        try {
            // Utilisation de Jackson ObjectMapper pour mapper la chaîne JSON vers un objet Java
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Adresses.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Adresses> getAllAdresses() {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL); // Utilisation de l'URL stockée en tant que variable globale
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //On vérifie que la connexion s'est effectivement faite avant de continuer
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //On a besoin de spécifier l'encodage UTF-8 lors de la création InputStreamReader → affichage des accents
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine; //Ligne récupérée

                //Tant que la boucle ne tombe pas sur une ligne vide, on continue de rajouter les lignes
                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                //On ferme la lecture à la première ligne vide
                in.close();
            } else {
                responseString.append("Erreur de réponse de l'API. Code : ").append(responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel à l'API : ").append(e.getMessage());
        }

        return parseJsonArray(responseString.toString());
    }

    private List<Adresses> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<Adresses>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
