package com.nc.portal.service;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private static final String URL_GET_OPERATOR = "operator/aboutme";
    private static final String URL_GET_CUSTOMER = "customer/aboutme";

    @Autowired
    RestTemplateUtil restTemplateUtil;

    public UserDTO getUserByName(HttpServletRequest request, Role role) {
        String URL;
        if (role == Role.CUSTOMER) {
            URL = URL_GET_CUSTOMER;
        } else {
            URL = URL_GET_OPERATOR;
        }
        try {
            ResponseEntity<UserDTO> response = restTemplateUtil.exchange(request, URL, null,
                    HttpMethod.GET, UserDTO.class);
            UserDTO userDTO = response.getBody();
            return userDTO;
        } catch (Exception e) {
            return null;
        }
    }

    public int updateUser(HttpServletRequest request, UserDTO userDTO, Role role) {
        String URL;
        if (role == Role.CUSTOMER) {
            URL = URL_GET_CUSTOMER;
        } else {
            URL = URL_GET_OPERATOR;
        }
        try {
            ResponseEntity<UserDTO> response = restTemplateUtil.exchange(request, URL, userDTO,
                    HttpMethod.PUT, UserDTO.class);
            int code = response.getStatusCodeValue();
            return code;
        } catch (Exception e) {
            return -1;
        }
    }

}
