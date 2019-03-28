package com.nc.portal.service;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

@Service
public class AuthService implements GlobalConstants {

    private String URL_AUTH;
    private String URL_CREATE;
    private String URL_CLEAR;

    public AuthService() {
        this.URL_AUTH = URL + "auth/role";
        this.URL_CREATE = URL + "user";
        this.URL_CLEAR = URL + "auth/clear";
    }

    /*private final String URL = "http://localhost:8082/auth/role";
    private final String URL_CREATE = "http://localhost:8082/user";
    private final String URL_CLEAR = "http://localhost:8082/auth/clear";*/
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Создает заголовок для передачи с кодировкой (Base64)
     *
     * @param user
     * @param password
     * @return
     */
    private HttpHeaders createHttpHeaders(String user, String password) {
        String notEncoded = user + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes(Charset.forName("US-ASCII")));
        //
        //UserDTO.setBasicAuth(encodedAuth);
        //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", encodedAuth);
        return headers;
    }

    /**
     * Запрос на получение роли пользователя
     */
    public void getRole(String username, String password) {
        try {
            HttpHeaders headers = createHttpHeaders(username, password);//new HttpHeaders();//createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_AUTH, HttpMethod.GET, request, String.class);
            System.out.println("Result - status " + response.getBody());
            UserDTO.staticRole = Role.valueOf(response.getBody());
            UserDTO.staticUsername = username;
        } catch (Exception e) {
            UserDTO.staticRole = Role.UNAUTHORIZED;
            UserDTO.staticUsername = "";
            System.out.println("** Exception: " + e.getMessage());
        }
    }

    /**
     * Запрос на регистрацию
     * Здесь надо поймать код от сервера(406 уже есть такой объект, 201 он создан)
     *
     * @param userDTO
     */
    public int createUser(UserDTO userDTO) /*throws JSONException*/ {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // create request body
            JSONObject request = new JSONObject();
            request.put("username", userDTO.getUsername());
            request.put("password", userDTO.getPassword());
            if (userDTO.getRole() == null) {
                request.put("role", "CUSTOMER");
            } else
                request.put("role", userDTO.getRole());

            HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_CREATE, HttpMethod.POST, entity, String.class);
            UserDTO.staticRole = Role.CUSTOMER;
            UserDTO.staticUsername = userDTO.getUsername();
            return response.getStatusCode().value();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            if (e instanceof HttpClientErrorException) {
                return ((HttpClientErrorException) e).getRawStatusCode();
            }
            //пока так
            return -1;
        }
    }

    public void logout() {
        try {
            // UserDTO.setBasicAuth("");
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_CLEAR, HttpMethod.GET, request, String.class);
            System.out.println("Result - status " + response.getBody());
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }
}
