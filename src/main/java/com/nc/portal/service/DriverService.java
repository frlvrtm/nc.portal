package com.nc.portal.service;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class DriverService implements GlobalConstants {
    
    private static final String URL_MY_ORDERS = "driver/myorders";

    @Autowired
    RestTemplateUtil restTemplateUtil;
    
    
/*    private final String URL_ONE_DRIVER;
    private final RestTemplate restTemplate = new RestTemplate();
    public DriverService() {
        this.URL_ONE_DRIVER = URL + "driver/name/?name=";
    }

    public UserDTO getUserDTO(String name) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<UserDTO> response = restTemplate.exchange(URL_ONE_DRIVER + name, HttpMethod.GET, request, UserDTO.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }*/

    /*
        получает все заказыкот адресованы текущему водителю
     */
    public List<OrdersDTO> getMyOrders(HttpServletRequest request) {
        try {
            ResponseEntity<OrdersDTO[]> response = restTemplateUtil.exchange(request, URL_MY_ORDERS, null, HttpMethod.GET, OrdersDTO[].class);
            List<OrdersDTO> list = Arrays.asList(response.getBody());
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }
    
}
