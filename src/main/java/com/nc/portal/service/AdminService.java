package com.nc.portal.service;

import com.nc.portal.model.DriverDTO;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final String URL = "http://localhost:8082/driver";
    private final String URL_CREATE = "http://localhost:8082/user/employee";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<DriverDTO> getAllDrivers() {
        try {
            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.APPLICATION_JSON);
            //  headers.set("Authorization", UserDTO.getBasicAuth());
            //.. HttpEntity<<DriverDTO[]> request = new HttpEntity<String>(headers);
            // ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
            ResponseEntity<DriverDTO[]> responseEntity = restTemplate.getForEntity(URL, DriverDTO[].class);
            DriverDTO[] listDrivers = responseEntity.getBody();
            List<DriverDTO> list = new ArrayList<>();
            for (DriverDTO driver : listDrivers
                 ) {
                list.add(driver);
            }
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }

    }

    public int createEmployee(UserDTO userDTO) /*throws JSONException*/ {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // create request body
            JSONObject request = new JSONObject();
            request.put("username", userDTO.getUsername());
            request.put("password", userDTO.getPassword());
            request.put("role", userDTO.getRole());
            request.put("firstName", userDTO.getUsername());
            request.put("lastName", userDTO.getUsername());
            request.put("phone", userDTO.getUsername());

            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_CREATE, HttpMethod.POST, entity, String.class);
            return response.getStatusCode().value();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            if (e instanceof HttpClientErrorException) {
                return ((HttpClientErrorException) e).getRawStatusCode();
            }
            return -1;
        }
    }
}
