package com.annuaire.softclient.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.annuaire.softclient.model.NewSites;
import com.annuaire.softclient.model.Sites;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;


public class SitesDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:7273/api/v1/sites";

    public List<Sites> getAllSites() {
        //La méthode nous permet de récupérer les sites de travail avec le GET de l'API (adresse)
        //On récupère une chaîne de caractères JSON (format donné par l'API) pour la découper en fonction des champs définis dans notre classe
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

    private static List<Sites> parseJsonArray(String jsonArray) {
        System.out.println("sites :" +jsonArray);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<Sites>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Une erreur s'est produite lors de la découpage du JSON . Veuillez réessayer plus tard.");
            return Collections.emptyList();
        }
    }


    public static void createSites(NewSites sites) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonSite = objectMapper.writeValueAsString(sites);
            byte[] input = jsonSite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
    }

    public Sites deleteSites(int idSite) throws IOException {
        URL url = new URL(API_URL + "/" + idSite);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // La suppression a réussi
            System.out.println("Le site de travail a été supprimé avec succès.");
        } else {
            // La suppression a échoué, lancer une exception avec le code de réponse
            throw new IOException("Échec de la suppression du chantier. Code de réponse : " + responseCode);
        }

        connection.disconnect();

        return null;
    }
    public Sites updateSites (int idSite, NewSites sites) throws IOException {
        URL url = new URL(API_URL + "/" + idSite);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonSite = objectMapper.writeValueAsString(sites);
            byte[] input = jsonSite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
        return null;
    }
    public List<Sites> searchSitesByName(String searchTerm) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/nomSite?nomSite=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8));
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
                responseString.append("Erreur de réponse de l'API sites. Code : ").append(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel à l'API sites : ").append(e.getMessage());
        }
        return parseJsonArray(responseString.toString());
    }

    public int getSiteIdByName(String siteName) {
        try {
            // Requête pour obtenir tous les sites
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lire la réponse
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convertir la réponse en liste de Sites
                List<Sites> sitesList = objectMapper.readValue(response.toString(), new TypeReference<List<Sites>>() {});

                // Parcourir la liste pour trouver le site avec le nom correspondant
                for (Sites site : sitesList) {
                    if (site.getNomSite().equals(siteName)) {
                        return site.getIdSite(); // Supposant que getId() retourne l'ID du site
                    }
                }
            } else {
                System.out.println("Erreur de réponse de l'API. Code : " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'appel à l'API : " + e.getMessage());
        }

        // Retourner -1 si le site n'est pas trouvé ou s'il y a eu une erreur
        return -1;
    }

    public static Sites parseJsonString(String jsonString) {
        try {
            // Utilisation de Jackson ObjectMapper pour mapper la chaîne JSON vers un objet Java
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Sites.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
