package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.service.PriceService;
import com.nc.portal.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class PriceController {

    @Autowired
    PriceService priceService;

    @RequestMapping(value = "price_page", method = RequestMethod.GET)
    public String getPricePage(HttpServletRequest request,
                               Model model) {

        OrdersDTO order = CookieUtil.getOrder(request);
        if (order != null) {
            model.addAttribute("pointFromCookie", order.getPointFrom());
            model.addAttribute("pointToCookie", order.getPointTo());
        }

        return "price_page";
    }

    @RequestMapping(value = "price_page", method = RequestMethod.POST)
    public String getPrice(@RequestParam("pointFrom") String pointFrom,
                         @RequestParam("pointTo") String pointTo,
                         HttpServletResponse response,
                         Model model) {

        Map result = priceService.getPrice(response, pointFrom, pointTo);
        model.addAttribute("Tariff", "tariff: " + result.get("tariff"));
        model.addAttribute("Distance", "distance: " +
                Math.round((double) result.get("distance") * 1000d) / 1000d);
        model.addAttribute("Message", "price: " + result.get("price"));

        return "price_page";
    }

}