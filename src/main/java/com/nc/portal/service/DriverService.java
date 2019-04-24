package com.nc.portal.service;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
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
    private static final String URL_DRIVERS = "driver";

    @Autowired
    RestTemplateUtil restTemplateUtil;

    /*
        получает все заказыкот адресованы текущему водителю
     */
    public List<OrdersDTO> getMyOrders(HttpServletRequest request) {
        try {
            ResponseEntity<OrdersDTO[]> response = restTemplateUtil.exchange(request, URL_MY_ORDERS, null,
                    HttpMethod.GET, OrdersDTO[].class);
            List<OrdersDTO> list = Arrays.asList(response.getBody());
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

    public List<UserDTO> getDrivers(HttpServletRequest request) {
        try {
            ResponseEntity<UserDTO[]> response = restTemplateUtil.exchange(request, URL_DRIVERS, null,
                    HttpMethod.GET, UserDTO[].class);
            List<UserDTO> list = Arrays.asList(response.getBody());
            return list;
        } catch (Exception e) {
            System.out.println("** Exception: " + e.getMessage());
            return null;
        }
    }

}
