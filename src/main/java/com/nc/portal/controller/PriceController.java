package com.nc.portal.controller;

import com.nc.portal.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//todo artem

@Controller
public class PriceController {

    @Autowired
    PriceService priceService;

    @RequestMapping(value = "price_page", method = RequestMethod.GET)
    public String getPricePage() {
        return "price_page";
    }

    @RequestMapping(value = "price_page", method = RequestMethod.POST)
    public void getPrice(@RequestParam("pointFrom") String pointFrom, @RequestParam("pointTo") String pointTo, Model model) {
        Double[] result = priceService.getPrice(pointFrom, pointTo);
        model.addAttribute("Tariff", "tariff: " + result[0]);
        model.addAttribute("Distance", "distance: " + result[1]);
        model.addAttribute("Message", "price: " + result[2]);
    }

}