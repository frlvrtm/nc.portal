package com.nc.portal.service;

import com.nc.portal.model.CarDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.UserUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.nc.portal.service.GlobalConstants.URL;

@Service
public class AdminService {

/*    private final String URL_ALL_EMPLOYEES = "http://localhost:8082/user";
    private final String URL_CARS = "http://localhost:8082/car";
    private final String URL_CREATE = "http://localhost:8082/user/employee";
    private final String URL_UPDATE = "http://localhost:8082/user/update";*/

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
            ResponseEntity<String> response = restTemplate.exchange(URL_ALL_EMPLOYEES, HttpMethod.GET, request, String.class);

            JSONArray jsonArray = new JSONArray(response.getBody());
            List<UserDTO> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(UserUtils.getUserOfJSON(jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public CarDTO[] getFreeCars() {
        try {
            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.APPLICATION_JSON);
            //  headers.set("Authorization", UserDTO.getBasicAuth());
            //.. HttpEntity<<DriverDTO[]> request = new HttpEntity<String>(headers);
            // ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
            ResponseEntity<CarDTO[]> responseEntity = restTemplate.getForEntity(URL_CARS, CarDTO[].class);
            CarDTO[] listCars = responseEntity.getBody();
            return listCars;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public boolean createEmployee(UserDTO userDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(UserUtils.getJSONOfUser(userDTO).toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_CREATE, HttpMethod.POST, entity, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        } catch (Exception e) {
            //Пока не знаю как обрабатывать данную ситуацию
            return false;
        }
    }


    public int updateUsers(List<UserDTO> listUserUpdate) {
        try {
            List<UserDTO> listDriverOld = getAllEmployees();

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < listDriverOld.size(); i++) {
                if (!listDriverOld.get(i).equals(listUserUpdate.get(i))) {
                    jsonArray.put(UserUtils.getJSONOfUser(listUserUpdate.get(i)));
                }
            }

            if (jsonArray.length() == 0)
                return 0;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(jsonArray.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_UPDATE, HttpMethod.PUT, entity, String.class);

            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getRawStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }

    public boolean deleteEmployee(String username) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject json = new JSONObject();
            json.put("username", username);

            HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_DELETE, HttpMethod.DELETE, entity, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        } catch (Exception e) {
            //Пока не знаю как обрабатывать данную ситуацию
            return false;
        }
    }
}
