package com.nc.portal.controller;

import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    @Autowired
    AccountService accountService;

    @GetMapping()
    public String getPageAddUser(Model model) {
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            model.addAttribute("userDTO", new UserDTO());
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            int responseCoode = accountService.createUser(userDTO);
            if(responseCoode == 406)
                model.addAttribute("errorMessage", "Name already taken");
            return "admin";
        } else
            return "error/access-denied";
    }
}