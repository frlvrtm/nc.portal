package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.RoleThreadLocal;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AuthService;
import com.nc.portal.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/reg")
public class RegController {

    @Autowired
    AuthService authService;

    @GetMapping
    public String getReg(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/registration";
    }

    @PostMapping
    public String createUser(@ModelAttribute UserDTO userDTO,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        int code = authService.createUser(request, response, userDTO);
        System.out.println("Status: " + code);
        return "redirect:/customer";
    }

}
