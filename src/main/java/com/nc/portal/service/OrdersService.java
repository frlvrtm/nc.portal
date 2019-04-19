package com.nc.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.portal.model.OrderStatus;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrdersService implements GlobalConstants {

    private static String URL_ORDERS = "orders/customer";
    private static String URL_ORDERS_ALL = "orders/all";
    private static String URL_CREATE_ORDERS = "orders/create";
    private static String URL_UPDATE_ORDER = "orders/update";

    @Autowired
    RestTemplateUtil restTemplateUtil;

    public List<OrdersDTO> getAllOrders(HttpServletRequest request) {
        try {
            ResponseEntity<OrdersDTO[]> exchange =
                    restTemplateUtil.exchange(request, URL_ORDERS_ALL, null, HttpMethod.GET, OrdersDTO[].class);
            List<OrdersDTO> list = Arrays.asList(exchange.getBody());
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<OrdersDTO> getOrdersByCust(HttpServletRequest request) {
        try {
            ResponseEntity<OrdersDTO[]> exchange =
                    restTemplateUtil.exchange(request, URL_ORDERS, null, HttpMethod.GET, OrdersDTO[].class);

            // ResponseEntity<OrdersDTO[]> responseEntity = restTemplate.getForEntity(GetURL, OrdersDTO[].class);
            List<OrdersDTO> list = Arrays.asList(exchange.getBody());
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public int createOrder(HttpServletRequest request, String name, String pointFrom, String pointTo, String description) {
        try {
            OrdersDTO order = new OrdersDTO(name, pointFrom, pointTo, description);
            order.setStatus(OrderStatus.OPEN.toString());

            ResponseEntity<OrdersDTO> exchange =
                    restTemplateUtil.exchange(request, URL_CREATE_ORDERS, order, HttpMethod.POST, OrdersDTO.class);

            //ResponseEntity<String> response = restTemplate.exchange(URL_CREATE_ORDERS, HttpMethod.POST, entity, String.class);
            int code = exchange.getStatusCode().value();
            return code;
        } catch (Exception e) {
            return -1;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public int updateStatus(HttpServletRequest request, String driver, String orderId, OrderStatus status) {
        try {
            OrdersDTO order = new OrdersDTO();
            order.setIdOrder(Integer.parseInt(orderId));
            if (status == OrderStatus.ASSIGNED) {
                order.setDriver(driver);
            }
            order.setStatus(status.toString());

            ResponseEntity<OrdersDTO> exchange =
                    restTemplateUtil.exchange(request, URL_UPDATE_ORDER, order, HttpMethod.PUT, OrdersDTO.class);

            int code = exchange.getStatusCode().value();
            return code;
        } catch (Exception e) {
            return -1;
        }
    }
}
