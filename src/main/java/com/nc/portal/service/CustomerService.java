package com.nc.portal.service;


import com.nc.portal.model.UserDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.nc.portal.service.GlobalConstants.URL;

@Service
public class CustomerService {
    private final String URL_ONE_CUST;
    private final RestTemplate restTemplate = new RestTemplate();
    public CustomerService() {

        this.URL_ONE_CUST= URL + "customer/name";
    }
    public UserDTO getUserByName(String name) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<UserDTO> response = restTemplate.exchange(URL_ONE_CUST+"?name="+name, HttpMethod.GET, request, UserDTO.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }
    public int updateUser(UserDTO userUpdate) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", UserDTO.staticUsername);
            json.put("firstName", userUpdate.getFirstName());
            json.put("lastName", userUpdate.getLastName());
            json.put("phone", userUpdate.getPhone());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_ONE_CUST, HttpMethod.POST, entity, String.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getRawStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }
}
