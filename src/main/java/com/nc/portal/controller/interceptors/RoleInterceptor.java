package com.nc.portal.controller.interceptors;


import com.nc.portal.model.AuthThreadLocal;
import com.nc.portal.model.Role;
import com.nc.portal.utils.CookieUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoleInterceptor extends HandlerInterceptorAdapter {

    private Role role;

    public RoleInterceptor(Role r) {
        role = r;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Role rolereq = CookieUtil.getRole(request);
        if (rolereq.equals(role)) {
            AuthThreadLocal.setAuth(CookieUtil.getAuth(request));
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}