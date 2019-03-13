package com.nc.portal.utils;

import com.nc.portal.model.UserDTO;
import org.json.JSONObject;

public class UserUtils {


    public static UserDTO getUserOfJSON(JSONObject json) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(json.getString("username"));
            userDTO.setRole(json.getString("role"));
            if (json.has("firstName")) {
                userDTO.setFirstName(json.getString("firstName"));
            }
            if (json.has("lastName")) {
                userDTO.setLastName(json.getString("lastName"));
            }
            if (json.has("phone")) {
                userDTO.setPhone(json.getString("phone"));
            }
            if (json.has("carNumber")) {
                userDTO.setCarNumber(json.getString("carNumber"));
            }
            //пока не используется
            if (json.has("realPoint")) {
                userDTO.setRealPoint(json.getString("realPoint"));
            }
            return userDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static JSONObject getJSONOfUser(UserDTO userDTO) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", userDTO.getUsername());
            if (!(userDTO.getPassword() == null))
                json.put("password", userDTO.getPassword());
            json.put("role", userDTO.getRole());
            json.put("firstName", userDTO.getFirstName());
            json.put("lastName", userDTO.getLastName());
            json.put("phone", userDTO.getPhone());
            if (!(userDTO.getCarNumber() == null))
                json.put("carNumber", userDTO.getCarNumber());
            return json;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
