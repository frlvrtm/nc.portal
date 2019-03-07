package com.nc.portal.service;

import com.nc.portal.model.OrdersDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class OrdersService {
    private final String URL = "http://localhost:8082/orders";
    private final RestTemplate restTemplate = new RestTemplate();

    public OrdersDTO[] getAllOrders() /*throws JSONException*/ {
        try {
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<OrdersDTO[]> responseEntity = restTemplate.getForEntity(URL, OrdersDTO[].class);
            OrdersDTO[] objects = responseEntity.getBody();
            return objects;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }
}
