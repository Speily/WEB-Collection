package com.kaishengit.controller;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(Model model){

        List<User> userList = userService.findAll();
        for(User user:userList){
            System.out.println(user.getAddress());
        }
        model.addAttribute("userList",userList);
        model.addAttribute("success","state");

        return "user/list";
    }

    @GetMapping("/add")
    public String add(){
        return "user/add";
    }

    @PostMapping("/add")
    public String add(User user, RedirectAttributes redirectAttributes){
        userService.add(user);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/user/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String del(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        userService.delById(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/user/list";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user/edit";
    }

    @GetMapping("/edit")
    public String edit(User user,RedirectAttributes redirectAttributes){
        userService.update(user);
        redirectAttributes.addFlashAttribute("message","更新成功");
        return "/user/list";
    }


}
