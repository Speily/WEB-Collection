package com.kaishengit.crm.controller.intercepter;


import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录状态拦截器
 * Created by SPL on 2017/7/19 0019.
 */
public class LoginIntercepter extends HandlerInterceptorAdapter {
    /**
     * 除了 / 之外其他请求,都要判断是否登录（session）
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();

        if(url.startsWith("/static/")){
            return true;
        }
        if("/".equals(url)){
            return true;
        } else{
            HttpSession session = request.getSession();
            if(session.getAttribute("currentUser") == null){
                response.sendRedirect("/");
                return false;
            }else{

                return true;
            }
        }

    }
}
