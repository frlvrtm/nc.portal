package com.nc.portal.service;

import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.model.Role;
import com.nc.portal.model.RoleThreadLocal;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.CookieUtil;
import com.nc.portal.utils.RestTemplateUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

@Service
public class AuthService implements GlobalConstants {

    private String URL_ROLE;
    private String URL_CREATE;
    private String URL_CLEAR;
    private String URL_AUTH;

    public AuthService() {
        this.URL_ROLE = "auth/role";
        this.URL_CREATE = URL + "user";
        this.URL_CLEAR = URL + "auth/clear";
        this.URL_AUTH = URL + "auth/login";
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    RestTemplateUtil restTemplateUtil;

    /**
     * Запрос на получение роли пользователя
     */
    public void getRole(String username, String password, HttpServletRequest req, HttpServletResponse res) {
        try {
            //Добавляем токен в поток
            restTemplateUtil.createToken(username, password);
            CookieUtil.create(res, CookieUtil.COOKIE_AUTH, AuthThreadLocal.getAuth().toString(), -1, req.getServerName());
            //Отправляем запрос
            ResponseEntity<String> response = restTemplateUtil.exchange(req, URL_ROLE, HttpMethod.GET, String.class);
            //Добавляем новый токен
            List<String> header = response.getHeaders().getValuesAsList(HttpHeaders.AUTHORIZATION);
            AuthThreadLocal.setAuth(header.get(0));

            Role role = Role.valueOf(response.getBody());

            RoleThreadLocal.setRole(role);
            CookieUtil.create(res, CookieUtil.COOKIE_AUTH, AuthThreadLocal.getAuth(), -1, req.getServerName());
            CookieUtil.create(res, CookieUtil.COOKIE_ROLE, RoleThreadLocal.getRole().toString(), -1, req.getServerName());

            //return response.getBody();
        } catch (Exception e) {
            //не знаю где надо будет обрабатывать ошибки 401 и 403, надо подумать над этим
            CookieUtil.create(res, CookieUtil.COOKIE_ROLE, Role.UNAUTHORIZED.toString(), -1, req.getServerName());
            RoleThreadLocal.setRole(Role.UNAUTHORIZED);
            System.out.println("** Exception getRole(): " + e.getMessage());
            // return null;
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
