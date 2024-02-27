package com.annuaire.softclient.dao;

import com.annuaire.softclient.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.json.JSONObject;

public class EmployeesDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:7273/api/v1/employees";

    public List<Employees> getAllEmployeesByServices(int idService) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "?idService=" + idService);
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
        List<Employees> allEmployees = parseJsonArray(responseString.toString());
        List<Employees> employeesByService = new ArrayList<>();

        for (Employees employee : allEmployees) {
            if (employee.getIdService() == idService) {
                employeesByService.add(employee);
            }
        }
        return employeesByService;
    }

    public List<Employees> getAllEmployees() {
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

    public void createEmployees(NewEmployees newEmployees) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonEmployees = objectMapper.writeValueAsString(newEmployees);
            byte[] input = jsonEmployees.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
            System.out.printf("out : " + outputStream);
        }

        int responseCode = connection.getResponseCode();
        System.out.println("responseCode: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();

    }

    public Employees deleteEmployees(int idEmploye) throws IOException {
        URL url = new URL(API_URL + "/" + idEmploye);
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

    public Employees updateEmployees (int idEmploye, NewEmployees employees) throws IOException {
        URL url = new URL (API_URL + "/" + idEmploye);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection ();
        connection.setRequestMethod ("PUT");
        connection.setRequestProperty ("Content-Type", "application/json");
        connection.setDoOutput (true);

        try (OutputStream outputStream = connection.getOutputStream ()) {
            String jsonSite = objectMapper.writeValueAsString (employees);
            byte[] input = jsonSite.getBytes ("utf-8");
            outputStream.write (input, 0, input.length);
        }

        int responseCode = connection.getResponseCode ();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException ("Échec de la requête : " + responseCode);
        }
        connection.disconnect ();
        return null;
    }

    public List<Employees> searchServiceByName(String searchTerm) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/searchEmployees?searchEmployees=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8));
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



    public static Employees parseJsonString(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Employees.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Employees> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<Employees>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


}


