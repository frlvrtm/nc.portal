package com.nc.portal.service;

import com.nc.portal.model.Account;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class AccountService {

    private final String URL_ACC = "http://localhost:8000/";
    private final String URL_CREATE_ACC = "http://localhost:8000/auth";
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
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + encodedAuth);
        return headers;
    }

    /**
     * Запрос на авторизацию (exchange)
     *
     * @param account
     */
    public void AuthRequest(Account account) {
        try {
            HttpHeaders headers = createHttpHeaders(account.getUsername(), account.getPassword());
            HttpEntity<String> request = new HttpEntity<String>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_ACC, HttpMethod.GET, request, String.class);
            System.out.println("Result - status " + response.getStatusCode());
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }

    /**
     * Запрос на регистрацию (postForEntity)
     *
     * @param account
     */
    public void AccCreationRequest(Account account) {
        try {
            HttpHeaders headers = createHttpHeaders(account.getUsername(), account.getPassword());
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.postForEntity(URL_CREATE_ACC, request, String.class);
            System.out.println("Result - status " + response.getStatusCode());
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }

}
