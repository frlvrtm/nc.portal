package com.nc.portal.controller;

import com.nc.portal.service.AuthService;
import com.nc.portal.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse resp) {
        authService.logout(request);
        CookieUtil.clear(request,resp);
        return "redirect:/auth?logout";
    }

}
