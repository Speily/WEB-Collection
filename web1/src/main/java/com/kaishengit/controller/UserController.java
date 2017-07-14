package com.kaishengit.controller;

import com.kaishengit.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")//相当于servlet地址
public class UserController {

    @RequestMapping("/list")//相当于servlet地址 http://localhost/user/java/list
    public String list(Model model){
        model.addAttribute("name","张三");
        return "user/list";//相当于重定向
    }
    //获取url?p=110&...的值
    @RequestMapping("/page")
    public String page(Integer p,String name){
        System.out.println(p);
        System.out.println(name);
        return "";
    }
    //获取url?p=110&...的值(基本数据类型)
    @RequestMapping("/page2")
    public String page2(@RequestParam(value = "p" ,defaultValue = "1",required = false) int pageNum){
        System.out.println(pageNum);
        return "";
    }
    //validate romote:/validateName 验证
    @RequestMapping("/validateName")
    @ResponseBody
    public String validateName(@PathVariable String username){
        if("tom".equals(username)){
            return "false";
        } else {
            return "true";
        }
    }

    @RequestMapping("/{id:\\d+}")//http://localhost/user/java/1
    public String add(@PathVariable Integer id){
        System.out.println("id-->" + id);
        return "user/add";
    }
    @RequestMapping("/{className:java|web}/{\\d+}/{name:\\w+}")
    public String show(@PathVariable String className,
                       @PathVariable String name){
        System.out.println(className+ "-"+ name);
        return "user/show";
    }


    @RequestMapping("/userlist")
    @ResponseBody
    public List<User> Userlist(@RequestParam(required = false,defaultValue = "1") int page){
        User user = new User();
        user.setAddress("上海");
        user.setId(1101);
        user.setName("张无忌");
        User user2 = new User();
        user2.setAddress("上海");
        user2.setId(1102);
        user2.setName("周芷若");
        List<User> userList = Arrays.asList(user,user2);
        return userList;
    }
}
