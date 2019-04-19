package com.nc.portal.controller;

import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.CustomerService;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;

    public String decodeName(String dec) {
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
    public String getOrders(Model model, HttpServletRequest request) {
        List<OrdersDTO> ordersDTO = ordersService.getOrdersByCust(request);
        model.addAttribute("orders", ordersDTO);
        return "customer/myorders";
    }

    @RequestMapping(value = "/aboutme", method = RequestMethod.GET)
    public String getProfile(Model model) {
        String username = decodeName(AuthThreadLocal.getAuth());
        UserDTO userDTO = customerService.getUserByName(username, "CUSTOMER");
        model.addAttribute("user", userDTO);
        return "customer/aboutme";
    }

    @RequestMapping(value = "/aboutme", method = RequestMethod.POST)
    public String setProfile(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("phone") String phone) {
        String username = decodeName(AuthThreadLocal.getAuth());
        int code = customerService.updateUser(username, firstName, lastName, phone, "CUSTOMER");
        return "redirect:/customer/aboutme";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrder1(Model model) {
        model.addAttribute("order", new OrdersDTO());
        return "customer/orderscreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder2(@RequestParam("pointFrom") String pointFrom,
                               @RequestParam("pointTo") String pointTo,
                               @RequestParam("description") String description,
                               Model model, HttpServletRequest request) {
        String username = decodeName(AuthThreadLocal.getAuth());
        ordersService.createOrder(request, username, pointFrom, pointTo, description);
        model.addAttribute("order", new OrdersDTO());
        return "redirect:/customer/myorders";
    }
}
