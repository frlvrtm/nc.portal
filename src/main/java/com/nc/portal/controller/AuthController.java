package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


//todo nikita
//удалить старые комменты
//com.nc.portal.controller.AuthController#getAuthPage должен просто возвращать страничку auth
//подправить auth.html - раздел <form> - добавить name; action="/auth"; th:action="@{/auth}" th:object="${userDTO}" - удалить это
//th:field="*{username}" - удалить, th:field="*{password}" - удалить; добавить <input type="hidden" name="role" value="customer">
//com.nc.portal.controller.AuthController#submit переименовать в getAuth
//+ найти способ как передать String login, String password в параметры метода..

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAuthPage() {
        UserDTO.staticRole = Role.UNAUTHORIZED;
        return "auth";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String getAuth(@RequestParam("username") String username,
                          @RequestParam("password") String password, Model model) {
        authService.getRole(username,password);
        if (UserDTO.staticRole.equals(Role.UNAUTHORIZED)) {
            model.addAttribute("errorMessage", "incorrect name or password");
            return "auth";
        } else {
            return "redirect:/" + UserDTO.staticRole.getUrl();
        }
    }
}


