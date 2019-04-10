package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.CustomerService;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;

    @GetMapping
    public String getPage() {

        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            return "customer";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/myorders", method = RequestMethod.GET)
    public String getOrders(Model model) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            OrdersDTO[] ordersDTO = ordersService.getOrdersByCust(UserDTO.staticUsername);
            model.addAttribute("orders", ordersDTO);
            return "myorders";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/aboutme", method = RequestMethod.GET)
    public String getProfile(Model model, HttpServletRequest request) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            UserDTO userDTO = customerService.getUserByName((String)request.getSession().getAttribute("x-auth-token"));
            model.addAttribute("user", userDTO);
            return "aboutme";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/aboutme", method = RequestMethod.POST)
    public String setProfile(@ModelAttribute UserDTO user) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            customerService.updateUser(user);
            return "empty";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)//works normally without annotation @GetMapping
    public String createOrder1(Model model) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            model.addAttribute("order", new OrdersDTO());
            return "orderscreate";
        }

        return "error/access-denied";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder2(@ModelAttribute OrdersDTO order, Model model) {
        if (UserDTO.staticRole.equals(Role.CUSTOMER)) {
            ordersService.createOrder(order);
            model.addAttribute("order", new OrdersDTO());
            return "empty";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "error/access-denied";
    }
}
