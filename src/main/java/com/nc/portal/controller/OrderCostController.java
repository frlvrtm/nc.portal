package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cost_page")
public class OrderCostController {

    @Autowired
    CostService costService;

    @GetMapping
    public String getCost_Page(Model model) {
        model.addAttribute("ordersDTO", new OrdersDTO());
        return "cost_page";
    }

    @PostMapping()
    public void submit(@ModelAttribute OrdersDTO ordersDTO, Model model) {
        costService.getInfo(ordersDTO);
        model.addAttribute("Message", "Delivery will cost you - " + ordersDTO.getPrice());
    }

}