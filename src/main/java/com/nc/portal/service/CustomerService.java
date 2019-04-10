package com.nc.portal.service;


import com.nc.portal.model.UserDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService implements GlobalConstants {
    private final String URL_ONE_CUST;
    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerService() {
        this.URL_ONE_CUST = URL + "customer/name";
    }
    private HttpHeaders createTokenHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-auth-token", token);
        return headers;
    }
    public UserDTO getUserByName(String token) {
        try {
            String name=token.split(":")[0];
            HttpHeaders headers = createTokenHttpHeaders(token);
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<UserDTO> response = restTemplate.exchange(URL_ONE_CUST + "?name=" + name, HttpMethod.GET, request, UserDTO.class);
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
            HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_ONE_CUST, HttpMethod.POST, entity, String.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getRawStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }
}
