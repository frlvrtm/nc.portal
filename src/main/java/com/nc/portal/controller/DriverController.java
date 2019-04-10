package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.DriverService;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/driver")
public class DriverController {
    @Autowired
    DriverService driverService;
    @Autowired
    OrdersService ordersService;

    @GetMapping
    public String getPage() {
        return "driver";
    }

    @RequestMapping(value = "/aboutDriver", method = RequestMethod.GET)
    public String getProfile(Model model) {
        if (UserDTO.staticRole.equals(Role.DRIVER)) {
            UserDTO userDTO = driverService.getUserDTO(UserDTO.staticUsername);
            model.addAttribute("user", userDTO);
            return "aboutDriver";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/driver", method = RequestMethod.POST)
    public void postData(@RequestParam("idOrder") int idOrder,
                         @RequestParam("customer") String customer,
                         @RequestParam("pointFrom") String pointFrom,
                         @RequestParam("pointTo") String pointTo,
                         @RequestParam("weight") double weight,
                         @RequestParam("price") double price,
                         @RequestParam("status") String status) {
        System.out.println(idOrder);
        System.out.println(customer);
        System.out.println(pointFrom);
        System.out.println(pointTo);
        System.out.println(weight);
        System.out.println(price);
        System.out.println(status);

    }

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String getOrder() {
//        OrdersDTO[] ordersDTO = driverService.getOrdersByDriver(UserDTO.staticUsername);
//    }
}
