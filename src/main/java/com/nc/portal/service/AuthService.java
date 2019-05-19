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

    public static String URL_ROLE = "auth/role";
    private static String URL_CREATE = "user/customers";
    private static String URL_LOGOUT = "auth/logout";

    //private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    RestTemplateUtil restTemplateUtil;

    public String getRole(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        try {
            //Добавляем токен в поток
            String token = createToken(username, password);
            AuthThreadLocal.setAuth(token);
            //Отправляем запрос
            ResponseEntity<String> exchange =
                    restTemplateUtil.exchange(request, URL_ROLE, null, HttpMethod.GET, String.class);
            //Добавляем новый токен
            List<String> header = exchange.getHeaders().getValuesAsList(HttpHeaders.AUTHORIZATION);
            AuthThreadLocal.setAuth(header.get(0));

            Role role = Role.valueOf(exchange.getBody());
            //Для того чтобы можно было узнать роль в AuthController.getAuth()
            RoleThreadLocal.setRole(role);
            //Добавляем куки
            CookieUtil.create(response, AuthThreadLocal.getAuth(), RoleThreadLocal.getRole().toString(), true);

            return exchange.getBody();
        } catch (Exception e) {
            //не знаю где надо будет обрабатывать ошибки 401 и 403, надо подумать над этим
            RoleThreadLocal.setRole(Role.UNAUTHORIZED);
            CookieUtil.create(response, "", Role.UNAUTHORIZED.toString(), true);
            System.out.println("** Exception getRole(): " + e.getMessage());
            return null;
        }
    }

    public int createUser(HttpServletRequest request, UserDTO userDTO) {
        try {
            ResponseEntity<UserDTO> exchange = restTemplateUtil.exchange(request, URL_CREATE, userDTO, HttpMethod.POST, UserDTO.class);
            int code = exchange.getStatusCode().value();
            return code;
        } catch (Exception e) {
            return -1;
        }
    }

    public void logout(HttpServletRequest request) {
        ResponseEntity<String> exchange = restTemplateUtil.exchange(request, URL_LOGOUT, null, HttpMethod.GET, String.class);
        int code = exchange.getStatusCode().value();
    }


    private String createToken(String login, String password) {
        String notEncoded = login + ":" + password;
        byte[] bytes = notEncoded.getBytes(Charset.forName("US-ASCII"));
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedAuth = "Basic " + encoder.encodeToString(bytes);
        return encodedAuth;
    }
}
