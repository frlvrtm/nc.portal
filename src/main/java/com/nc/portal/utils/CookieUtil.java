package com.nc.portal.utils;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final String COOKIE_NAME = "AUTH";

    public static void create(HttpServletResponse httpServletResponse, String name, String value, Integer maxAge, String domain) {

        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);

        if (maxAge > 0) {
            //-1 значит куки действуют до закрытия браузера
            cookie.setMaxAge(maxAge);
        }

        httpServletResponse.addCookie(cookie);
    }

    public static void clear(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        if (getValueByName(request, name) != null) {
            Cookie cookie = new Cookie(name, "");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }
    }

    public static String getValueByName(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }
}

