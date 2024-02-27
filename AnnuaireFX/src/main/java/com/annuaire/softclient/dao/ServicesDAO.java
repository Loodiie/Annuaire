package com.annuaire.softclient.dao;

import com.annuaire.softclient.model.NewServices;
import com.annuaire.softclient.model.Services;
import com.annuaire.softclient.model.Sites;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicesDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:7273/api/v1/services";

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

    public List<Services> getAllServices() {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL);
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

        return parseJsonArray(responseString.toString());
    }

    public int getServiceIdByName(String serviceName){
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
                List<Services> servicesList = objectMapper.readValue(response.toString(), new TypeReference<List<Services>>() {});

                // Parcourir la liste pour trouver le site avec le nom correspondant
                for (Services services : servicesList) {
                    if (services.getNomService().equals(serviceName)) {
                        return services.getIdService(); // Supposant que getId() retourne l'ID du site
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

    public Services getServicesById(int idService) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/" + idService);
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
        Services responseServiceSite = parseJsonString(responseString.toString());
        return responseServiceSite;
    }

    public void createServices(NewServices newServices) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newServices);
            byte[] input = jsonWorksite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        System.out.println("responseCode: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();

    }

    public Sites deleteServices(int idServices) throws IOException {
        URL url = new URL(API_URL + "/" + idServices);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Le site de travail a été supprimé avec succès.");
        } else {
            throw new IOException("Échec de la suppression du chantier. Code de réponse : " + responseCode);
        }
        connection.disconnect();
        return null;
    }

    public Sites updateServices (int idServices, NewServices newServices) throws IOException {
        URL url = new URL(API_URL + "/" + idServices);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newServices);
            byte[] input = jsonWorksite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
        return null;
    }

    public List<Services> searchServiceSiteByName(String searchTerm) {
        StringBuilder responseString = new StringBuilder();

        try {
            // Construire l'URL avec le terme de recherche encodé
            URL url = new URL(API_URL + "/searchServices?searchServices=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8));
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
                responseString.append("Erreur de réponse de l'API services. Code : ").append(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel à l'API services : ").append(e.getMessage());
        }

        return parseJsonArray(responseString.toString());
    }


    public static Services parseJsonString(String jsonString) {
        try {
            // Utilisation de Jackson ObjectMapper pour mapper la chaîne JSON vers un objet Java
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Services.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
