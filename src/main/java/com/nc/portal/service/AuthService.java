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

    private static String URL_ROLE = "auth/role";
    ;
    private static String URL_CREATE = "user/customer";
    private static String URL_LOGOUT = "auth/logout";
    // private String URL_AUTH;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    RestTemplateUtil restTemplateUtil;

    /**
     * Запрос на получение роли пользователя
     */
    public void getRole(HttpServletRequest request, HttpServletResponse res, String username, String password) {
        try {
            //Добавляем токен в поток
            restTemplateUtil.createToken(username, password);
            //Отправляем запрос
            ResponseEntity<String> response = restTemplateUtil.exchange(request, URL_ROLE, null, HttpMethod.GET, String.class);
            //Добавляем новый токен
            List<String> header = response.getHeaders().getValuesAsList(HttpHeaders.AUTHORIZATION);
            AuthThreadLocal.setAuth(header.get(0));

            Role role = Role.valueOf(response.getBody());
            //Для того чтобы можно было узнать роль в AuthController.getAuth()
            RoleThreadLocal.setRole(role);
            //Добавляем куки
            CookieUtil.create(res, AuthThreadLocal.getAuth(), RoleThreadLocal.getRole().toString(), -1, request.getServerName());

            //return response.getBody();
        } catch (Exception e) {
            //не знаю где надо будет обрабатывать ошибки 401 и 403, надо подумать над этим
            RoleThreadLocal.setRole(Role.UNAUTHORIZED);
            CookieUtil.create(res, "", Role.UNAUTHORIZED.toString(), -1, request.getServerName());
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

    public void logout(HttpServletRequest request) {
        restTemplateUtil.exchange(request, URL_LOGOUT, null, HttpMethod.GET, String.class);
    }
}
