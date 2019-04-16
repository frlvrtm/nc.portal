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
import java.nio.charset.Charset;
import java.util.Base64;

@Component
public class RestTemplateUtil {

    public static final String BASE_URL = "http://localhost:8082/";
    private RestTemplate restTemplate;

    public RestTemplateUtil() {
        //this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
        this.restTemplate = new RestTemplate();
    }

    public <T> ResponseEntity<T> exchange(HttpServletRequest request,
                                          String url,
                                          T requestBody,
                                          HttpMethod method,
                                          Class<T> responseType) {

        HttpEntity<T> entity = requestBody == null ?
                new HttpEntity<>(addHeaders(request)) :
                new HttpEntity<>(requestBody, addHeaders(request));
        try {
            return restTemplate.exchange(BASE_URL + url, method, entity, responseType);
        } catch (HttpStatusCodeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

/*    //пока так
    public <T> int postForEntity(HttpServletRequest req,
                                 String url,
                                 T requestBody,
                                 Class<T> responseType) {
        HttpEntity<T> entity = new HttpEntity<>(requestBody, addHeaders(req));
        try {
            ResponseEntity<T> response = restTemplate.postForEntity(BASE_URL + url, entity, responseType);
            return response.getStatusCode().value();
        } catch (HttpStatusCodeException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public <T> int patchForEntity(HttpServletRequest req,
                                String url,
                                T requestBody,
                                Class<T> responseType) {
        HttpEntity<T> entity = new HttpEntity<>(requestBody, addHeaders(req));
        try {
            ResponseEntity<T> response = restTemplate.(BASE_URL + url, entity, responseType);
            return restTemplate.patchForObject(BASE_URL + url, entity, responseType);
        } catch (HttpStatusCodeException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }*/

/*    public <T> T postForEntity(HttpServletRequest req,
                               String url,
                               T requestBody,
                               Class<T> responseType) {
        HttpEntity<T> entity = new HttpEntity<>(requestBody, addHeaders(req));
        try {
            T t = restTemplate.postForEntity(BASE_URL + url, entity, responseType);
            return t;
        } catch (HttpStatusCodeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }*/
/*
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

    private HttpHeaders addHeaders(HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        //если запрос с Session
        String cookie = CookieUtil.getAuth(request);
        //Если идет из getRole()
        String token = AuthThreadLocal.getAuth();
        //проверка что токен не пуст еще будет
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (cookie != null && cookie != "") {
            headers.add(HttpHeaders.AUTHORIZATION, cookie);
        } else {
            headers.add(HttpHeaders.AUTHORIZATION, token);
        }
        //нужно ли?
        //headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }




}
