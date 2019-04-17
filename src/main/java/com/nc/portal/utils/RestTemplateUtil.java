package com.nc.portal.utils;


import com.nc.portal.model.AuthThreadLocal;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;

@Component
public class RestTemplateUtil {

    public static final String BASE_URL = "http://localhost:8082/";
    private RestTemplate restTemplate;

    public RestTemplateUtil() {
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
            ResponseEntity<T> responseEntity = restTemplate.exchange(BASE_URL + url, method, entity, responseType);
            return responseEntity;
        } catch (HttpStatusCodeException e) {
            //Вернем с ответ с ошибкой
            ResponseEntity<T> responseEntity = new ResponseEntity(e.getStatusCode());
            return responseEntity;
        }
    }

    private HttpHeaders addHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        //нужно ли?
        headers.setContentType(MediaType.APPLICATION_JSON);
        //если запрос на вход в систему
        if (request.getRequestURI().equals("/auth")) {
            String token = AuthThreadLocal.getAuth();
            headers.add(HttpHeaders.AUTHORIZATION, token);
        } else {    //если запрос с Session
            String cookie = CookieUtil.getAuth(request);
            if (cookie != null && cookie != "") {
                headers.add(HttpHeaders.AUTHORIZATION, cookie);
            }
        }
        return headers;
    }
}
