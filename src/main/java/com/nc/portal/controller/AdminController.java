package com.nc.portal.controller;

import com.nc.portal.model.CarDTO;
import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AuthService;
import com.nc.portal.service.AdminService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    @Autowired
    AuthService authService;

    @Autowired
    AdminService adminService;

    //Словарь для добавления аттрибутов в модель
    private Map<String, String> dictionary = new HashMap<String, String>();

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
        authService.getRole(userDTO);
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            return "redirect:/admin/page";
        } else {
            String message = "";
            if (UserDTO.staticRole.equals(Role.UNAUTHORIZED)) {
                message = "Incorrect name or password";
            } else {
                message = "Incorrect role " + UserDTO.staticRole.toString();
                authService.logout();
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
            model = getAttributeModel(model);
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/page")
    public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            int code = adminService.createEmployee(userDTO);
            switch (code) {
                case 201:
                    dictionary.put("infoMessage", "User added");
                    break;
                case 406:
                    dictionary.put("errorMessage", "Name already taken");
                    break;
                case -1:
                    dictionary.put("errorMessage", "Unexpected error");
                    break;
            }
            dictionary.put("flag1", "1");
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute("form") ListUser listUser) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            int code = adminService.updateUsers(listUser.getList());
            switch (code) {
                case 0:
                    dictionary.put("infoMessage", "No changes, no need to update");
                    break;
                case 1:
                    dictionary.put("errorMessage", "1 car selected for 2 drivers");
                    break;
                case 201:
                    dictionary.put("infoMessage", "Updated");
                    break;
                case 400:
                    dictionary.put("errorMessage", "Bad request");
                    break;
                case -1:
                    dictionary.put("errorMessage", "Unexpected error");
                    break;
            }
            dictionary.put("flag2", "2");
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("form") ListUser listUser, @RequestParam String username) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            adminService.deleteEmployee(username);
            dictionary.put("infoMessage", username + " deleted");
            dictionary.put("flag2", "2");
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }

    @PostMapping("/car")
    public String addCar(@ModelAttribute() CarDTO carDTO) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            int code = adminService.addCar(carDTO);
            switch (code) {
                case 201:
                    dictionary.put("infoMessage", "Car added");
                    break;
                case 406:
                    dictionary.put("errorMessage", "Number already taken");
                    break;
                case -1:
                    dictionary.put("errorMessage", "Unexpected error");
                    break;
            }
            dictionary.put("flag3", "3");
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }

    private Model getAttributeModel(Model model) {
        //все сотрудники
        model.addAttribute("form", new ListUser(adminService.getAllEmployees()));
        //форма для заполнения нового юзера
        model.addAttribute("userDTO", new UserDTO());
        //форма для заполнения новой машины
        model.addAttribute("carDTO", new CarDTO());
        //Список всех машин без водителей
        model.addAttribute("cars", adminService.getFreeCars());
        //alert
        for (String key : dictionary.keySet()) {
            model.addAttribute(key, dictionary.get(key));
        }
        //очистить словарь
        dictionary = new HashMap<String, String>();
        return model;
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