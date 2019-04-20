package com.nc.portal.controller;

import com.nc.portal.model.*;
import com.nc.portal.service.DriverService;
import com.nc.portal.service.OrdersService;
import com.nc.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OperatorController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    UserService userService;
    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public String getPage() {
        return "operator/index";
    }

    @RequestMapping(value = "/operator/orders", method = RequestMethod.GET)
    public String getPage(HttpServletRequest request, Model model) {
        List<OrdersDTO> orders = ordersService.getAllOrders(request);
        List<UserDTO> drivers = driverService.getDrivers(request);
        model.addAttribute("drivers", drivers);
        model.addAttribute("orders", orders);
        model.addAttribute("order", new OrdersDTO());
        return "operator/operator";
    }

    @RequestMapping(value = "/operator/assigned", method = RequestMethod.POST)
    public String assignedDriver(@RequestParam("driver") String driver,
                                 @RequestParam("idOrder") String orderId,
                                 HttpServletRequest request,
                                 Model model) {
        int code = ordersService.updateStatus(request, driver, orderId, OrderStatus.ASSIGNED);
        if (code == 202) {
            //----------------------------------------------------------------------
            List<OrdersDTO> ordersDTO = ordersService.getAllOrders(request);
            List<UserDTO> drivers = driverService.getDrivers(request);
            model.addAttribute("drivers", drivers);
            model.addAttribute("orders", ordersDTO);
            model.addAttribute("order", new OrdersDTO());
            model.addAttribute("Message", "ACCEPTED! Order status has been changed to assigned.");
            //----------------------------------------------------------------------
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "operator/operator";
    }

    @RequestMapping(value = "/operator/close", method = RequestMethod.POST)
    public String closeOrder(@RequestParam("idOrder") String orderId,
                             HttpServletRequest request,
                             Model model) {
        int code = ordersService.updateStatus(request, null, orderId, OrderStatus.CLOSED);
        if (code == 202) {
            //----------------------------------------------------------------------
            List<OrdersDTO> ordersDTO = ordersService.getAllOrders(request);
            List<UserDTO> drivers = driverService.getDrivers(request);
            model.addAttribute("drivers", drivers);
            model.addAttribute("orders", ordersDTO);
            model.addAttribute("order", new OrdersDTO());
            model.addAttribute("Message", "ACCEPTED! Order status has been changed to closed.");
            //----------------------------------------------------------------------
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "operator/operator";
    }

    @RequestMapping(value = "/operator/profile", method = RequestMethod.GET)
    public String getProfile(HttpServletRequest request, Model model) {
        UserDTO userDTO = userService.getUserByName(request, Role.OPERATOR);
        model.addAttribute("user", userDTO);
        return "operator/profile";
    }

    @RequestMapping(value = "/operator/profile", method = RequestMethod.POST)
    public String setProfile(HttpServletRequest request,
                             @ModelAttribute("user") UserDTO userDTO,
                             Model model) {
        int code = userService.updateUser(request, userDTO, Role.OPERATOR);
        if (code == 202) {
            model.addAttribute("Message", "Data saved successfully!");
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "redirect:/operator/profile";
    }
}
