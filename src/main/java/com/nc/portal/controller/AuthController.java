package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//todo nikita
//аннотацию @RequestMapping перенести над методами; туда же добавить method = либо get либо post; @GetMapping, @PostMapping
//ctrl+alt+l - автоформатирование кода в идеи; убрать лишние строчки
//AuthService переименовать в AuthService; shift+f6 - переименует везде
//getAuth переименовать в getAuthPage; удалить атрибут model; все что в методе.. оставить только return auth page

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAuthPage(Model model) {
        if (UserDTO.staticRole.equals(Role.UNAUTHORIZED)) {
            model.addAttribute("userDTO", new UserDTO());
            return "auth";
        } else {
            return "redirect:/" + UserDTO.staticRole.getUrl();
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String submit(@ModelAttribute UserDTO userDTO, Model model) {
        authService.getRole(userDTO);
        if (UserDTO.staticRole.equals(Role.UNAUTHORIZED)) {
            model.addAttribute("errorMessage", "incorrect name or password");
            return "auth";
        } else {
            return "redirect:/" + UserDTO.staticRole.getUrl();
        }
    }
}


