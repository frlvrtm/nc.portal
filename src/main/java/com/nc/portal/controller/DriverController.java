package com.nc.portal.controller;

import com.nc.portal.model.OrderStatus;
import com.nc.portal.service.DriverService;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DriverController {
    @Autowired
    DriverService driverService;
    @Autowired
    OrdersService ordersService;

    @RequestMapping(value = "/driver", method = RequestMethod.GET)
    public String getPage() {
        return "driver/startPageDriver";
    }

    @RequestMapping(value = "/driver/aboutDriver", method = RequestMethod.GET)
    public String getProfile(Model model) {
        //UserDTO userDTO = driverService.getUserDTO(UserDTO.staticUsername);
        //model.addAttribute("user", userDTO);
        return "driver/aboutDriver";
    }

    @RequestMapping(value = "/driver/inprogress", method = RequestMethod.POST)
    public String setStatus(@RequestParam("orderId") String orderId,
                            Model model,
                            HttpServletRequest request) {

        int code = ordersService.updateStatus(request, null, orderId, OrderStatus.INPROGRESS);
        //if code = ACCEPTED то прошло успешно, иначе какая-то ошибка

        return "driver/aboutDriver";
    }

    @RequestMapping(value = "/driver/resolved", method = RequestMethod.POST)
    public String closeOrder(@RequestParam("orderId") String orderId,
                             HttpServletRequest request,
                             Model model) {
//----------------------------------------------------------------------
        int code = ordersService.updateStatus(request, null, orderId, OrderStatus.RESOLVED);
        //if code = ACCEPTED то прошло успешно, иначе какая-то ошибка
//----------------------------------------------------------------------
        return "driver/aboutDriver";
    }

}
