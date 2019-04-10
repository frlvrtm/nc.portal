package com.nc.portal.service;

import com.nc.portal.model.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DriverService implements GlobalConstants {
    private final String URL_ONE_DRIVER;
    private final RestTemplate restTemplate = new RestTemplate();
    public DriverService() {
        this.URL_ONE_DRIVER = URL + "driver/name/?name=";
    }

    public UserDTO getUserDTO(String name) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<UserDTO> response = restTemplate.exchange(URL_ONE_DRIVER + name, HttpMethod.GET, request, UserDTO.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

}
