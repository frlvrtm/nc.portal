package com.nc.portal.service;

import com.nc.portal.model.UserDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

@Service
public class AccountService {

    private final String URL_ACC = "http://localhost:8000/";
    private final String URL = "http://localhost:8082/auth/role";
    private final String URL_CREATE = "http://localhost:8082/auth";
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", encodedAuth);
        return headers;
    }

    /**
     * Запрос на авторизацию (exchange)
     *
     * @param account
     */
    /*public void AuthRequest(UserDTO account) {
        try {
            HttpHeaders headers = createHttpHeaders(account.getUsername(), account.getPassword());//new HttpHeaders();//createHttpHeaders(account.getUsername(), account.getPassword());
            HttpEntity<String> request = new HttpEntity<String>(headers);
            // ResponseEntity<String> response = restTemplate.exchange(URL_ACC, HttpMethod.GET, request, String.class);

            //restTemplate.getInterceptors().add(
                   // new BasicAuthorizationInterceptor(account.getUsername(), account.getPassword()));
            ResponseEntity<String> response = restTemplate.exchange(URL_ACC, HttpMethod.GET, request, String.class);

            System.out.println(response);
            System.out.println("Result - status " + response.getStatusCode());
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }*/

    /**
     * Запрос на регистрацию (postForEntity)
     *
     * @param userDTO
     */
    public void getRole(UserDTO userDTO) {
        try {
            HttpHeaders headers = createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());//new HttpHeaders();//createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());
            HttpEntity<String> request = new HttpEntity<String>(headers);
            //String response = restTemplate.getForObject("http://localhost/hello/", String.class);
            //ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
            //System.out.println("Result - status " + response.getStatusCode());
            System.out.println("Result - status " + response.getBody());
            userDTO.setRole(response.getBody());
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }

    public void createUser(UserDTO userDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // create request body
            JSONObject request = new JSONObject();
            request.put("username", userDTO.getUsername());
            request.put("password", userDTO.getPassword());
            request.put("role", "CUSTOMER");

            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
           // ResponseEntity<String> response = restTemplate.put(url, entity);


            // send request and parse result
            ResponseEntity<String> loginResponse = restTemplate
                    .exchange(URL_CREATE, HttpMethod.POST, entity, String.class);
  /*          if (loginResponse.getStatusCode() == HttpStatus.OK) {
                JSONObject userJson = new JSONObject(loginResponse.getBody());
            } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // nono... bad credentials
            }*/


/*            HttpHeaders headers = new HttpHeaders();// createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());//new HttpHeaders();//createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());
            HttpEntity<String> request = new HttpEntity<String>(headers);
            //String response = restTemplate.getForObject("http://localhost/hello/", String.class);
            //ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
            ResponseEntity<String> response = restTemplate.exchange(URL_CREATE, HttpMethod.POST, request, String.class);
            //System.out.println("Result - status " + response.getStatusCode());
            System.out.println("Result - status " + response.getBody());
            userDTO.setRole(response.getBody());*/
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }
}
