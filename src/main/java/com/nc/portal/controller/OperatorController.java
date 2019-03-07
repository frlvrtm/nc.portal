package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OperatorController {

    @Autowired
    OrdersService ordersService;

    @GetMapping
    @RequestMapping(value = "/operator")
    public String getPage(Model model) {
        OrdersDTO[] ob= ordersService.getAllOrders();
        model.addAttribute("orders", ob);
        return "operator";
    }
}
