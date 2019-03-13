package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String getAuth(Model model) {
        if(UserDTO.staticRole.equals(Role.UNAUTHORIZED)){
            model.addAttribute("userDTO", new UserDTO());
            return (UserDTO.staticRole.getUrl());
        }else{
            return "redirect:/" + UserDTO.staticRole.getUrl();
        }
    }

    @PostMapping
    public String submit(@ModelAttribute UserDTO userDTO, Model model) {
        accountService.getRole(userDTO);
        if(UserDTO.staticRole.equals(Role.UNAUTHORIZED)){
            model.addAttribute("errorMessage", "incorrect name or password");
            return (UserDTO.staticRole.getUrl());
        }else{
            return "redirect:/" + UserDTO.staticRole.getUrl();
        }
    }
}


