package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    OrdersService ordersService;

    @GetMapping
    public String getPage(Model model) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            OrdersDTO[] ordersDTO = ordersService.getAllOrders();
            model.addAttribute("orders", ordersDTO);
            return "customer";
        }
        else
            return "error/access-denied";
    }

    @RequestMapping(value = "/create")
    @GetMapping
    public String updateOrder(Model model) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            model.addAttribute("order", new OrdersDTO());
            return "orderscreate";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "redirect:/auth";
    }
}
