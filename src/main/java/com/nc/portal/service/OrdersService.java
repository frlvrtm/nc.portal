package com.nc.portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.portal.model.OrdersDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OrdersService {
    private final String URL = "http://localhost:8082/orders";
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public OrdersDTO[] getAllOrders() /*throws JSONException*/ {
        try {
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<OrdersDTO[]> responseEntity = restTemplate.getForEntity(URL, OrdersDTO[].class);
            OrdersDTO[] objects = responseEntity.getBody();
            return objects;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return new OrdersDTO[0];
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
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            System.out.println("Result - status " + response.getStatusCode());
        }
        catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
        }
    }
}
