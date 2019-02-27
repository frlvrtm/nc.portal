package com.nc.portal.controller;

import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    AccountService accountService;

    @GetMapping
    @RequestMapping(value = "/auth")
    public String FormAuthentication(Model model) {
        model.addAttribute("account", new UserDTO());
        return "auth";
    }

    @PostMapping
    @RequestMapping(value = "/page")
    public String submit(@ModelAttribute UserDTO userDTO) {
        accountService.getRole(userDTO);
        String r = userDTO.getRole();
        if (r.equals("ROLE_ADMIN"))
        {
            return "admin";
        }
        else
        {
            if (r.equals("ROLE_OPERATOR"))
            {
                return "operator";
            }
            else
            {
                if (r.equals("ROLE_DRIVER"))
                {
                    return "driver";
                }
                else
                {
                    if(r.equals("ROLE_CUSTOMER"))
                    {
                        return "customer";
                    }
                    else
                    {
                        return "auth";
                    }
                }
            }
        }
    }

}


