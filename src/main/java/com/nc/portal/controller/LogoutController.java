package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

    @Autowired
    AuthService authService;

    @GetMapping
    public String logout() {
        authService.logout();
        UserDTO.staticRole = Role.UNAUTHORIZED;
        UserDTO.staticUsername = "";
        return "redirect:/auth?logout";
    }

}
