package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by SPL on 2017/7/19 0019.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/")
    public String loginView(){
        return "login";
    }

    /**
     * 登录验证
     * @param tel
     * @param password
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(String tel, String password, HttpSession httpSession){

        try {
            User user = userService.findByTelLoadDept(tel,password);

            httpSession.setAttribute("currentUser",user);
            return AjaxResult.success();

        } catch (ServiceException ex){
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * 用户退出
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("message","您已退出系统！");
        return "redirect:login";
    }
    /**
     * 用户设置
     * @return
     */
    @GetMapping("/profile")
    public String userProfile(){
        return "profile";
    }
}
