package com.nc.portal.service;

import com.nc.portal.model.CarDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {

    private static final String URL_EMPLOYEES = "user/employees";
    private static final String URL_CARS_FREE = "car/free";
    private static final String URL_CARS_ALL = "car/all";
    private static final String URL_CARS_ADD = "car";

    @Autowired
    RestTemplateUtil restTemplateUtil;

    public List<UserDTO> getAllEmployees(HttpServletRequest request) {
        try {
            ResponseEntity<UserDTO[]> response = restTemplateUtil.exchange(request, URL_EMPLOYEES, null, HttpMethod.GET, UserDTO[].class);
            List<UserDTO> list = Arrays.asList(response.getBody());
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public List<CarDTO> getFreeCars(HttpServletRequest request) {
        try {
            ResponseEntity<CarDTO[]> response = restTemplateUtil.exchange(request, URL_CARS_FREE, null, HttpMethod.GET, CarDTO[].class);
            List<CarDTO> list = Arrays.asList(response.getBody());
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public List<CarDTO> getAllCars(HttpServletRequest request) {
        try {
            ResponseEntity<CarDTO[]> response = restTemplateUtil.exchange(request, URL_CARS_ALL, null, HttpMethod.GET, CarDTO[].class);
            List<CarDTO> list = Arrays.asList(response.getBody());
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public int createEmployee(HttpServletRequest request, UserDTO userDTO) {
        try {
            ResponseEntity<UserDTO> response = restTemplateUtil.exchange(request, URL_EMPLOYEES, userDTO, HttpMethod.POST, UserDTO.class);
            return response.getStatusCode().value();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }

    public int updateUsers(HttpServletRequest request, List<UserDTO> listUpdate) {
        try {
            List<UserDTO> listOld = getAllEmployees(request);

            UserDTO[] users = new UserDTO[listOld.size()];
            int count = 0;
            for (int i = 0; i < listOld.size(); i++) {
                if (!listOld.get(i).equals(listUpdate.get(i))) {
                    users[count] = listUpdate.get(i);
                    count++;
                }
            }
            if (count == 0)
                return 0;
            users = Arrays.copyOf(users, count);

            ResponseEntity<UserDTO[]> response = restTemplateUtil.exchange(request, URL_EMPLOYEES, users, HttpMethod.PUT, UserDTO[].class);
            return response.getStatusCode().value();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }

    public int deleteEmployee(HttpServletRequest request, String username) {
        try {
            ResponseEntity<String> response = restTemplateUtil.exchange(request, URL_EMPLOYEES, username, HttpMethod.DELETE, String.class);
            return response.getStatusCode().value();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }

    public int addCar(HttpServletRequest request, CarDTO carDTO) {
        try {
            ResponseEntity<CarDTO> response = restTemplateUtil.exchange(request, URL_CARS_ADD, carDTO, HttpMethod.POST, CarDTO.class);
            return response.getStatusCode().value();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }


}
