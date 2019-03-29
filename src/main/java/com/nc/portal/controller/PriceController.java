package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//todo artem
//com.nc.portal.controller.PriceController - @RequestMapping перенести надо методами, добавить method = RequestMethod.GET or POST, @GetMapping, @PostMapping - убрать
//com.nc.portal.controller.PriceController#getCost_Page - переименовать в getPricePage; input parametrs убрать, model.addAttribute("ordersDTO", new OrdersDTO()); - убрать
//price_page.html - убрать th:field="*{pointFrom}, th:field="*{pointTo},
//добавить name = "pointFrom", name = "pointTo" - это позволить юзать значения во входных параметрах методов через @RequestParam("parameter_name");
//th:action="@{/price_page}" th:object="${ordersDTO}" - убрать, в action прописать "/price_page"
//com.nc.portal.controller.PriceController#submit переименовать в getPrice; input parameters - pointFrom, pointTo; model - оставляем.. чтобы message кидать

@Controller
@RequestMapping(value = "/price_page")
public class PriceController {

    @Autowired
    PriceService priceService;

    @GetMapping
    public String getCost_Page(Model model) {
        model.addAttribute("ordersDTO", new OrdersDTO());
        return "price_page";
    }

    @PostMapping
    public void submit(@ModelAttribute OrdersDTO ordersDTO, Model model) {
        priceService.getPrice(ordersDTO);
        model.addAttribute("Message", "Delivery will cost you - " + ordersDTO.getPrice());
    }

}