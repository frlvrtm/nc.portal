package com.nc.portal.service;

import com.nc.portal.model.OrdersDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PriceService implements GlobalConstants {

    private final String URL_PRICE;

    public PriceService() {
        this.URL_PRICE = URL + "price";
    }

    public void getPrice(OrdersDTO ordersDTO) {
        try {
            JSONObject json = new JSONObject();
            json.put("address1", ordersDTO.getPointFrom());
            json.put("address2", ordersDTO.getPointTo());
            //json.put("tariff", ordersDTO.getWeight());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(URL_PRICE, HttpMethod.POST, entity, String.class);
            ordersDTO.setPrice(Double.parseDouble(response.getBody()));
        } catch (Exception e) {
            log.debug("** Exception: " + e.getMessage());
        }

    }

}
