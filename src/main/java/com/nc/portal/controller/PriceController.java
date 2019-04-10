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
        double result = priceService.getPrice(pointFrom, pointTo);
        model.addAttribute("Message", "Delivery will cost you - " + result);
    }

}