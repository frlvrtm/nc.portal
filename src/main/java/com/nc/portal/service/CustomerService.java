package com.nc.portal.service;


import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    private final String URL_ONE_CUST;
    private final String URL_ONE_OPER;
    private String URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerService() {
        this.URL_ONE_CUST = GlobalConstants.URL + "customer/name";
        this.URL_ONE_OPER = GlobalConstants.URL + "operator/name";
    }
    private HttpHeaders createTokenHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-auth-token", token);
        return headers;
    }
    public UserDTO getUserByName(String token, String role) {
        if (role == "CUSTOMER") {
            URL = URL_ONE_CUST;
        } else
            URL = URL_ONE_OPER;
        try {
            String name=token.split(":")[0];
            HttpHeaders headers = createTokenHttpHeaders(token);
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<UserDTO> response = restTemplate.exchange(URL + "?name=" + name, HttpMethod.GET, request, UserDTO.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public int updateUser(String token, String fname, String sname, String phone, String role) {
        if (role == "CUSTOMER") {
            URL = URL_ONE_CUST;
        } else
            URL = URL_ONE_OPER;
        try {
            String name=token.split(":")[0];
            UserDTO userDTO = new UserDTO(name, fname, sname, phone);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
            ResponseEntity<UserDTO> response = restTemplate.exchange(URL, HttpMethod.POST, entity, UserDTO.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getRawStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }
}
