package com.nc.portal.controller;

import com.nc.portal.controller.validators.ListUserValidator;
import com.nc.portal.model.CarDTO;
import com.nc.portal.model.ListUser;
import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AdminService;
import com.nc.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    ListUserValidator listUserValidator;

    //Словарь для добавления аттрибутов в модель
    private Map<String, String> dictionary = new HashMap<>();

    @InitBinder("form")
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        if (request.getMethod().equals("POST")) {
            //проверка на дубликаты
            binder.setValidator(listUserValidator);
            //из "" сдеть null
            binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }

    /**
     * Метод для отображения страницы входа для админа
     *
     * @param model
     * @return
     */
    @GetMapping()
    public String logInForAdmin(Model model) {
        if (UserDTO.staticRole == Role.ADMIN) {
            return "redirect:/admin/page";
        }
        return "authadmin";
    }

    /**
     * Форма для проверки введенных данных для админа
     *
     * @param model
     * @return
     */
    @PostMapping
    public String getAuth(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        authService.getRole(username, password);
        if (UserDTO.staticRole == Role.ADMIN) {
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
    public String getPageAdd(Model model) {
        if (UserDTO.staticRole.equals(Role.ADMIN)) {
            //все сотрудники
            model.addAttribute("form", new ListUser(adminService.getAllEmployees()));
            //форма для заполнения нового юзера
            model.addAttribute("userDTO", new UserDTO());
            //форма для заполнения новой машины
            model.addAttribute("carDTO", new CarDTO());
            //Список всех машин без водителей
            model.addAttribute("carsFree", adminService.getFreeCars());
            //Список всех машин
            model.addAttribute("carsAll", adminService.getAllCars());
            //alert
            for (String key : dictionary.keySet()) {
                model.addAttribute(key, dictionary.get(key));
            }
            //очистить словарь
            dictionary = new HashMap<>();
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/page")
    public String addUser(@ModelAttribute UserDTO userDTO) {
        if (UserDTO.staticRole == Role.ADMIN) {
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
    public String update(@ModelAttribute("form") @Valid ListUser listUser, BindingResult bindingResult) {
        if (UserDTO.staticRole == Role.ADMIN) {
            if (bindingResult.hasErrors()) {
                dictionary.put("errorMessage", "1 car selected for 2 drivers");
                dictionary.put("flag2", "2");
                return "redirect:/admin/page";
            }
            List<UserDTO> list = listUser.getList();
            int code = adminService.updateUsers(list);
            switch (code) {
                case 0:
                    dictionary.put("infoMessage", "No changes, no need to update");
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
}