package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.result.AjaxResult;
import com.kaishengit.util.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPL on 2017/7/17 0017.
 */
@Controller
@RequestMapping("/manage/account")
public class AccountController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String accountList() {
        return "manage/accounts";
    }

    /**
     * 加载目录书
     */
    @PostMapping("/depts.json")
    @ResponseBody
    public List<Ztree> findAll(){
        List<Dept> deptList = deptService.findAll();
        List<Ztree> ztreeList = new ArrayList<>();
        for (Dept dept:deptList){
            Ztree ztree = new Ztree();
            ztree.setId(dept.getId());
            ztree.setName(dept.getDeptName());
            ztree.setpId(dept.getDeptId());
            ztreeList.add(ztree);
        }

        return ztreeList;

    }
    /**
     * 新增子目录
     */
    @PostMapping("/dept/add")
    @ResponseBody
    public AjaxResult addDept(Dept dept){

        deptService.add(dept);

        return AjaxResult.success();
    }

    /**
     * 新增员工（用户）
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addUser(User user,Integer[] deptId){
        userService.add(user,deptId);
        return AjaxResult.success();
    }

}
