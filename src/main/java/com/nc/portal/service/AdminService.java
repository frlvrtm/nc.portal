package com.nc.portal.service;

import com.nc.portal.model.CarDTO;
import com.nc.portal.model.ListUser;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.JSONUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private final String URL_CARS;
    private final String URL_CREATE;
    private final String URL_UPDATE;
    private final String URL_DELETE;

    public AdminService() {
        this.URL_ALL_EMPLOYEES = URL + "user";
        this.URL_CARS = URL + "car";
        this.URL_CREATE = URL + "user";
        this.URL_UPDATE = URL + "user/update";
        this.URL_DELETE = URL + "user/delete";
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public List<UserDTO> getAllEmployees() {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<UserDTO[]> response = restTemplate.exchange(URL_ALL_EMPLOYEES, HttpMethod.GET, request, UserDTO[].class);

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

    public CarDTO[] getFreeCars() {
        try {
            ResponseEntity<CarDTO[]> responseEntity = restTemplate.getForEntity(URL_CARS, CarDTO[].class);
            CarDTO[] listCars = responseEntity.getBody();
            return listCars;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public int createEmployee(UserDTO userDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO , headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_CREATE, HttpMethod.POST, entity, String.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getRawStatusCode();
        } catch (Exception e) {
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
            return e.getRawStatusCode();
        } catch (Exception e) {
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
            return e.getRawStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }

    public int addCar(CarDTO carDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CarDTO> entity = new HttpEntity<>(carDTO, headers);
            ResponseEntity<CarDTO> response = restTemplate.exchange(URL_CARS, HttpMethod.POST, entity, CarDTO.class);
            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getRawStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }


}
