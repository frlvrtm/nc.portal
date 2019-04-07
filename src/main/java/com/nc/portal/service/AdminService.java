package com.nc.portal.service;

import com.nc.portal.model.CarDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nc.portal.service.GlobalConstants.URL;

@Service
public class AdminService {

    private final String URL_ALL_EMPLOYEES;
    private final String URL_CARS_FREE;
    private final String URL_CARS_ALL;
    private final String URL_CREATE;
    private final String URL_UPDATE;
    private final String URL_DELETE;

    public AdminService() {
        this.URL_ALL_EMPLOYEES =  "user";
        this.URL_CARS_FREE = URL + "car/free";
        this.URL_CARS_ALL = URL + "car/all";
        this.URL_CREATE = URL + "user";
        this.URL_UPDATE = URL + "user/update";
        this.URL_DELETE = URL + "user/delete";
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    RestTemplateUtil restTemplateUtil;

    public List<UserDTO> getAllEmployees() {
        try {
            //ResponseEntity<UserDTO[]> response = restTemplate.getForEntity(URL_ALL_EMPLOYEES, UserDTO[].class);

            ResponseEntity<UserDTO[]> response = restTemplateUtil.exchange(URL_ALL_EMPLOYEES, HttpMethod.GET, UserDTO[].class);

            List<UserDTO> list = new ArrayList<>();
            if (response.getBody() != null) {
                list = Arrays.asList(response.getBody());
            }
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public List<CarDTO> getFreeCars() {
        try {
            ResponseEntity<CarDTO[]> response = restTemplate.getForEntity(URL_CARS_FREE, CarDTO[].class);
            List<CarDTO> list = new ArrayList<>();
            if (response.getBody() != null) {
                list = Arrays.asList(response.getBody());
            }
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public List<CarDTO> getAllCars() {
        try {
            ResponseEntity<CarDTO[]> response = restTemplate.getForEntity(URL_CARS_ALL, CarDTO[].class);
            List<CarDTO> list = new ArrayList<>();
            if (response.getBody() != null) {
                list = Arrays.asList(response.getBody());
            }
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public int createEmployee(UserDTO userDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(URL_CREATE, entity, String.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            System.out.println("** Exception: " + e.getMessage());
            return e.getRawStatusCode();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }

    public int updateUsers(List<UserDTO> listUpdate) {
        try {
            List<UserDTO> listOld = getAllEmployees();

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

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDTO[]> entity = new HttpEntity<>(users, headers);
            ResponseEntity<UserDTO[]> response = restTemplate.exchange(URL_UPDATE, HttpMethod.PUT, entity, UserDTO[].class);

            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            System.out.println("** Exception: " + e.getMessage());
            return e.getRawStatusCode();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }

    public int deleteEmployee(String username) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(username, headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_DELETE, HttpMethod.DELETE, entity, String.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            System.out.println("** Exception: " + e.getMessage());
            return e.getRawStatusCode();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }

    public int addCar(CarDTO carDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CarDTO> entity = new HttpEntity<>(carDTO, headers);
            ResponseEntity<CarDTO> response = restTemplate.postForEntity(URL_CARS_FREE, entity, CarDTO.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            System.out.println("** Exception: " + e.getMessage());
            return e.getRawStatusCode();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return -1;
        }
    }


}
