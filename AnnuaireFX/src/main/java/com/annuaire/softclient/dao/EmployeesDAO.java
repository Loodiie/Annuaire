package com.annuaire.softclient.dao;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeesDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/employees";
}
