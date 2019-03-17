package com.nc.portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;

@Service
public class OrdersService implements GlobalConstants {

    private String URL_ORDERS;
    private String URL_CREATE_ORDERS;
    public OrdersService() {
        this.URL_ORDERS = URL + "orders/";
        this.URL_CREATE_ORDERS=URL_ORDERS + "create/";
    }

    //private final String URL = "http://localhost:8082/orders";
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public OrdersDTO[] getAllOrders() /*throws JSONException*/ {
        try {
            ResponseEntity<OrdersDTO[]> responseEntity = restTemplate.getForEntity(URL_ORDERS, OrdersDTO[].class);
            OrdersDTO[] objects = responseEntity.getBody();
            return objects;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return new OrdersDTO[0];
        }
    }

    public OrdersDTO[] getOrdersByCust(String custname) {
        try {
            String GetURL = URL_ORDERS+"/customer?" +"custname=" + custname;
            ResponseEntity<OrdersDTO[]> responseEntity = restTemplate.getForEntity(GetURL, OrdersDTO[].class);
            OrdersDTO[] objects = responseEntity.getBody();
            return objects;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return new OrdersDTO[0];
        }
    }
    public void createOrder(OrdersDTO order)
    {
        try {
            order.setCustomer(UserDTO.staticUsername);
            order.setStatus("open");
            Long curTime = System.currentTimeMillis();
            order.setStart_time(new Date(curTime));
            HttpHeaders headers = new HttpHeaders();//new HttpHeaders();//createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());
            headers.setContentType(MediaType.APPLICATION_JSON);

            // create request body
            String request = mapper.writeValueAsString(order);

            HttpEntity<String> entity = new HttpEntity<String>(request, headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_CREATE_ORDERS, HttpMethod.POST, entity, String.class);
            System.out.println("Result - status " + response.getStatusCode());
        }
        catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }

    public void updateOrders(OrdersDTO ordersDTO)
    {
        try {
            HttpHeaders headers = new HttpHeaders();//new HttpHeaders();//createHttpHeaders(userDTO.getUsername(), userDTO.getPassword());
            headers.setContentType(MediaType.APPLICATION_JSON);

            // create request body
            String request = mapper.writeValueAsString(ordersDTO);

            HttpEntity<String> entity = new HttpEntity<String>(request, headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_ORDERS, HttpMethod.POST, entity, String.class);
            System.out.println("Result - status " + response.getStatusCode());
        }
        catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }
}
