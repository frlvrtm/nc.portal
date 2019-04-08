package com.nc.portal.utils;


import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.service.AuthService;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Enumeration;

@Component
public class RestTemplateUtil {

    public static final String BASE_URL = "http://localhost:8082/";
    private RestTemplate restTemplate;

    public RestTemplateUtil() {
        //this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
        this.restTemplate = new RestTemplate();
    }

    public <T> ResponseEntity<T> exchange(HttpServletRequest req,
                                          String url,
                                          HttpMethod method,
                                          Class<T> responseType) {
        HttpEntity<String> entity = new HttpEntity<>(addHeaders(req));
        try {
            return restTemplate.exchange(BASE_URL + url, method, entity, responseType);
        } catch (HttpStatusCodeException e) {
            //AuthService.sendRedirectIfError(e, req, res);
            System.out.println(e.getMessage());
            return null;
        }
    }


/*    public <T> T postForObject(HttpServletRequest req, HttpServletResponse res, String additionUrl,
                               T requestBody,
                               Class<T> responseType) {
        HttpEntity<T> entity = new HttpEntity<>(requestBody, addHeaders(req, res));
        try {
            T t = restTemplate.postForObject(BASE_URL + additionUrl, entity, responseType);
            return t;

        } catch (HttpStatusCodeException e) {
            //проверяем, если пишла 500, но нет хедеа, значит причина не в полях
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR && (e.getResponseHeaders().get("Error message").get(0) != null)) {
                String errorMessage = e.getResponseHeaders().get("Error message").get(0);
                throw new IllegalArgumentException(errorMessage);
            } else {
                AuthService.sendRedirectIfError(e, req, res);
                return null;
            }
        }
    }

    public <T> T patchForObject(HttpServletRequest req, HttpServletResponse res, String additionUrl,
                                T requestBody,
                                Class<T> responseType) {
        HttpEntity<T> entity = new HttpEntity<>(requestBody, addHeaders(req, res));
        try {
            return restTemplate.patchForObject(BASE_URL + additionUrl, entity, responseType);
        } catch (HttpStatusCodeException e) {
            AuthService.sendRedirectIfError(e, req, res);
            return null;
        }
    }*/


    //остальные методы так же

    private HttpHeaders addHeaders(HttpServletRequest req/*, HttpServletResponse res*/) {
        HttpHeaders headers = new HttpHeaders();
        String cookie = CookieUtil.getValueByName(req, CookieUtil.COOKIE_AUTH);
        String token = AuthThreadLocal.getAuth();
        //проверка что токен не пуст еще будет
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (cookie != null) {
            headers.add(HttpHeaders.AUTHORIZATION, cookie);
        } else {
            headers.add(HttpHeaders.AUTHORIZATION, token);
        }
        return headers;
    }

    public void createToken(String user, String password) {
        String notEncoded = user + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes(Charset.forName("US-ASCII")));
        AuthThreadLocal.setAuth(encodedAuth);
    }

}
