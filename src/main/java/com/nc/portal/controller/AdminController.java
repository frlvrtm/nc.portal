package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import com.nc.portal.service.AdminService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    @Autowired
    AccountService accountService;

    @Autowired
    AdminService adminService;

    /**
     * Метод для отображения страницы входа для админа
     *
     * @param model
     * @return
     */
    @GetMapping()
    public String logInForAdmin(Model model) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            return "redirect:/admin/page";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "authadmin";
    }

    /**
     * Форма для проверки введенных данных для админа
     *
     * @param userDTO
     * @param model
     * @return
     */
    @PostMapping
    public String submit(@ModelAttribute UserDTO userDTO, Model model) {
        accountService.getRole(userDTO);
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            return "redirect:/admin/page";
        } else {
            String message = "";
            if (UserDTO.staticRole.equals(Role.UNAUTHORIZED)) {
                message = "Incorrect name or password";
            } else {
                message = "Incorrect role " + UserDTO.staticRole.toString();
                accountService.logout();
                UserDTO.staticRole = Role.UNAUTHORIZED;
            }
            model.addAttribute("errorMessage", message);
            return "authadmin";
        }
    }

    /*
     * Страница админа
     *
     * @param model
     * @return
     */
    @GetMapping("/page")
    public String getPageAddUser(Model model) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            model.addAttribute("form", new ListUser(adminService.getAllEmployees()));
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("cars", adminService.getFreeCars());
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/page")
    public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            if(adminService.createEmployee(userDTO)){
                model.addAttribute("infoMessage", "User added");
                model.addAttribute("userDTO", new UserDTO());
            }else{
                model.addAttribute("errorMessage", "Name already taken");
            }
            model.addAttribute("form", new ListUser(adminService.getAllEmployees()));
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("cars", adminService.getFreeCars());
            return "admin";
        } else
            return "error/access-denied";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute("form") ListUser listUser) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            int code = adminService.updateUsers(listUser.getList());
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("form") ListUser listUser,@RequestParam String username) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            adminService.deleteEmployee(username);
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }



    /**
     * Внутренний класс для завертывания List<UserDTO> в объект и отправку на форму(html)
     */
    @Data
    public static class ListUser {
        private List<UserDTO> list;

        public ListUser() {
            this.list = new ArrayList<>();
        }

        public ListUser(List<UserDTO> list) {
            this.list = list;
        }
    }


}