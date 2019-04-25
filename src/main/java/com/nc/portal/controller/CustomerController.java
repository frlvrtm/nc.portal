package com.nc.portal.controller;

import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.CustomerService;
import com.nc.portal.service.OrdersService;
import com.nc.portal.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;
    @Autowired
    PriceService priceService;

    private OrdersDTO createOrder = new OrdersDTO();

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
                             @RequestParam("phone") String phone,
                             Model model) {
        String username = decodeName(AuthThreadLocal.getAuth());
        int code = customerService.updateUser(username, firstName, lastName, phone, "CUSTOMER");
        if (code == 201) {
            model.addAttribute("message", "Data saved successfully!");
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "redirect:/customer/aboutme";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createOrderViewPage(Model model) {
        model.addAttribute("order", new OrdersDTO());
        return "customer/orderscreate";
    }

    @RequestMapping(value = "/viewprice", method = RequestMethod.GET)
    public String cancelOrder(Model model) {
        model.addAttribute("order", new OrdersDTO());
        return "customer/orderscreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(Model model, HttpServletRequest request) {
        ordersService.createOrder(request, createOrder);
        model.addAttribute("message", "Order created");
        model.addAttribute("order", new OrdersDTO());
        return "customer/orderscreate";
    }

    @RequestMapping(value = "/viewprice", method = RequestMethod.POST)
    public String getPrice(@RequestParam("pointFrom") String pointFrom,
                           @RequestParam("pointTo") String pointTo,
                           @RequestParam("description") String description,
                           Model model/*, HttpServletRequest request*/) {

        Map result = priceService.getPrice(pointFrom, pointTo);
        if(result.get("price")!=null) {
            double price = Double.parseDouble(result.get("price").toString());
            createOrder = new OrdersDTO(price, pointFrom, pointTo, description);
            //Для формы с прайсом
            model.addAttribute("createOrder", true);
            model.addAttribute("Tariff", "Tariff: " + result.get("tariff"));
            model.addAttribute("Distance", "Distance: " +
                    Math.round((double)result.get("distance") * 1000d) / 1000d);
            model.addAttribute("Price", "Price: " + price);

            model.addAttribute("order", new OrdersDTO());
        }
        else
        {
            model.addAttribute("errorMessage", "Address not found!.");
            model.addAttribute("order", new OrdersDTO());
            return "customer/orderscreate";
        }
        return "customer/orderscreate";
    }
}
