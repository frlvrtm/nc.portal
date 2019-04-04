package com.nc.portal.controller.validators;

import com.nc.portal.model.ListUser;
import com.nc.portal.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ListUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;//AdminController.ListUser.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ListUser listUser = (ListUser) target;
        List<UserDTO> list = listUser.getList();
        //Проверка на дубликаты carName
        boolean duplicates = false;
        for (int j = 0; j < list.size(); j++) {
            for (int k = j + 1; k < list.size(); k++) {
                if ((list.get(k).getCarNumber() != null) &&
                        (list.get(j).getCarNumber() != null))
                    if (k != j && list.get(k).getCarNumber().equals(list.get(j).getCarNumber())) {
                        duplicates = true;
                        break;
                    }
            }
        }
        if (duplicates) {
            errors.rejectValue("list", "test.");
            //errors.
        }

    }
}
