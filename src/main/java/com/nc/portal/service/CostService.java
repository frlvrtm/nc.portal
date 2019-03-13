package com.nc.portal.service;

import com.nc.portal.model.OrdersDTO;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CostService implements GlobalConstants {

    private String URL_COST;


    public CostService() {
        this.URL_COST = URL + "cost";
    }

    public void getInfo(OrdersDTO ordersDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject request = new JSONObject();
            //request.put("url", "https://geocode-maps.yandex.ru/1.x/?apikey=07aaccb9-d30b-458f-8c06-49710fbacfcd&format=json&geocode=" + ordersDTO.getPoint_from() + "&results=1");
            request.put("point_from", ordersDTO.getPoint_from());
            /*request.put("point_to", ordersDTO.getPoint_to());
            request.put("weight", ordersDTO.getWeight());*/
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(URL_COST, HttpMethod.POST, entity, String.class);
            System.out.println("Result - status " + response.getStatusCode());
        }
        catch (Exception e)
        {
            System.out.println("** Exception: " + e.getMessage());
        }

    }

}
