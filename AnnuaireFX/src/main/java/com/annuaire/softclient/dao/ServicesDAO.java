package com.annuaire.softclient.dao;

import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.model.Sites;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicesDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/services";

    public List<Services> getAllServicesBySites(int idSite) {
        StringBuilder responseString = new StringBuilder();

        try {
            // Création de l'URL avec l'ID du site en tant que paramètre
            URL url = new URL(API_URL + "?idSite=" + idSite);
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
            responseString.append("Erreur lors de l'appel à l'API : ").append(e.getMessage());
        }

        List<Services> allServices = parseJsonArray(responseString.toString());
        List<Services> servicesBySite = new ArrayList<>();

        for (Services service : allServices) {
            if (service.getIdSite() == idSite) { // Vérifie si l'ID du site correspond
                servicesBySite.add(service);
            }
        }
        return servicesBySite;
    }


    private List<Services> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<Services>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
