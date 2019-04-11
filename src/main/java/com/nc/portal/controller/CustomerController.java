package com.nc.portal.controller;

import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.model.OrdersDTO;
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

import java.nio.charset.Charset;
import java.util.Base64;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;
    public String decodeName(String dec)
    {
        byte[] base64Token = dec.split(" ")[1].getBytes(Charset.forName("US-ASCII"));
        byte[] decoded = Base64.getDecoder().decode(base64Token);
        String token = new String(decoded);
        return token.split(":")[0];
    }
    @GetMapping
    public String getPage() {
        return "customer/customer";
    }

    @RequestMapping(value = "/myorders", method = RequestMethod.GET)
    public String getOrders(Model model) {
        String username =decodeName(AuthThreadLocal.getAuth());
        OrdersDTO[] ordersDTO = ordersService.getOrdersByCust(username);
        model.addAttribute("orders", ordersDTO);
        return "customer/myorders";
    }

    @RequestMapping(value = "/aboutme", method = RequestMethod.GET)
    public String getProfile(Model model) {
        String username =decodeName(AuthThreadLocal.getAuth());
        UserDTO userDTO = customerService.getUserByName(username);
        model.addAttribute("user", userDTO);
        return "customer/aboutme";
    }

    @RequestMapping(value = "/aboutme", method = RequestMethod.POST)
    public String setProfile(@ModelAttribute UserDTO user) {
        customerService.updateUser(user);
        return "customer/empty";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrder1(Model model) {
        model.addAttribute("order", new OrdersDTO());
        return "customer/orderscreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder2(@ModelAttribute OrdersDTO order, Model model) {
        ordersService.createOrder(order);
        model.addAttribute("order", new OrdersDTO());
        return "empty";
    }
}
