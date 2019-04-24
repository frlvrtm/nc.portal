package com.nc.portal.controller;

import com.nc.portal.model.OrderStatus;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.DriverService;
import com.nc.portal.service.OrdersService;
import com.nc.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DriverController {
    @Autowired
    DriverService driverService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/driver", method = RequestMethod.GET)
    public String getPage() {
        return "driver/startPageDriver";
    }

    @RequestMapping(value = "/driver/aboutDriver", method = RequestMethod.GET)
    public String getProfile(HttpServletRequest request, Model model) {
        UserDTO userDTO = userService.getUserByName(request, Role.DRIVER);
        model.addAttribute("user", userDTO);
        return "driver/aboutDriver";
    }

    @RequestMapping(value = "/driver/aboutDriver", method = RequestMethod.POST)
    public String setProfile(HttpServletRequest request,
                             @ModelAttribute("user") UserDTO userDTO,
                             Model model) {
        int code = userService.updateUser(request, userDTO, Role.DRIVER);
        if (code == 202) {
            model.addAttribute("Message", "Data saved successfully!");
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "redirect:/driver/aboutDriver";
    }

    @RequestMapping(value = "/driver/orders", method = RequestMethod.GET)
    public String getPage(HttpServletRequest request, Model model) {
        List<OrdersDTO> orders = driverService.getMyOrders(request);
        model.addAttribute("orders", orders);
        model.addAttribute("order", new OrdersDTO());
        return "driver/driver";
    }

    @RequestMapping(value = "/driver/inprogress", method = RequestMethod.POST)
    public String setStatus(@RequestParam("idOrder") String orderId,
                            Model model,
                            HttpServletRequest request) {
        int code = ordersService.updateStatus(request, null, orderId, OrderStatus.INPROGRESS);
        if (code == 202) {
            List<OrdersDTO> orders = driverService.getMyOrders(request);
            model.addAttribute("orders", orders);
            model.addAttribute("order", new OrdersDTO());
            model.addAttribute("Message", "ACCEPTED! Order status has been changed to in progress.");
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }

        return "driver/driver";
    }

    @RequestMapping(value = "/driver/resolved", method = RequestMethod.POST)
    public String closeOrder(@RequestParam("idOrder") String orderId,
                             HttpServletRequest request,
                             Model model) {
        int code = ordersService.updateStatus(request, null, orderId, OrderStatus.RESOLVED);
        if (code == 202) {
            List<OrdersDTO> orders = driverService.getMyOrders(request);
            model.addAttribute("orders", orders);
            model.addAttribute("order", new OrdersDTO());
            model.addAttribute("Message", "ACCEPTED! Order status has been changed to resolved.");
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "driver/driver";
    }

}
