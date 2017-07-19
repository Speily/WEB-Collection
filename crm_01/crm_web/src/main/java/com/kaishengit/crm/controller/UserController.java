package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.result.AjaxResult;
import com.kaishengit.result.DataTableResult;
import com.kaishengit.util.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工控制器UserController
 * Created by SPL on 2017/7/17 0017.
 */
@Controller
@RequestMapping("/manage/user")
public class UserController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String accountList() {
        return "manage/user";
    }

    /**
     * 加载Ztree目录树
     */
    @PostMapping("/depts.json")
    @ResponseBody
    public List<Ztree> findAll() {
        List<Dept> deptList = deptService.findAll();
        List<Ztree> ztreeList = new ArrayList<>();
        for (Dept dept : deptList) {
            Ztree ztree = new Ztree();
            ztree.setId(dept.getId());
            ztree.setName(dept.getDeptName());
            ztree.setpId(dept.getDeptId());
            ztreeList.add(ztree);
        }

        return ztreeList;

    }

    /**
     * 目录树
     * 新增子目录
     */
    @PostMapping("/dept/add")
    @ResponseBody
    public AjaxResult addDept(Dept dept) {

        deptService.add(dept);

        return AjaxResult.success();
    }


    /*
     * 异步加载员工列表
     */
    @GetMapping("/load.json")
    @ResponseBody
    public DataTableResult<User> loadUser(HttpServletRequest req) {

        String draw = req.getParameter("draw");
        String deptId = req.getParameter("deptId");

        Integer id = null;
        if (!StringUtils.isEmpty(deptId)) {
            id = Integer.valueOf(deptId);
        }
        //获取User的总记录数
        Long total  = userService.count();
        //查询deptid对应数量
        Long filtedTotal  = userService.countByDeptId(id);
        //查询deptid对应数据
        List<User> userList = userService.findByDeptId(id);

        return new DataTableResult(draw, total, filtedTotal, userList);
    }


    /**
     * 新增员工（用户）
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addUser(User user, Integer[] deptId) {
        userService.add(user, deptId);
        return AjaxResult.success();
    }

    /**
     * 删除员工（用户）
     */
    @PostMapping("/del/{userId:\\d+}")
    @ResponseBody
    public AjaxResult dellUser(@PathVariable Integer userId) {
        userService.dellById(userId);
        return AjaxResult.success();
    }
}
