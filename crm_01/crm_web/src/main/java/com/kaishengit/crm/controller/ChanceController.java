package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.entity.*;
import com.kaishengit.crm.exception.NotFoundException;
import com.kaishengit.crm.exception.UnableException;
import com.kaishengit.crm.service.ChanceService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.TaskService;
import com.kaishengit.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 机会控制器
 */
@Controller
@RequestMapping("/chance")
public class ChanceController {

    @Autowired
    private ChanceService chanceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    /**
     * 查询机会
     *
     * @param keyword
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/my")
    public String myChanceView(@RequestParam(required = false, defaultValue = "") String keyword,
                               @RequestParam(required = false, value = "p", defaultValue = "1") Integer pageNo,
                               Model model,
                               HttpSession session) {
        User user = (User) session.getAttribute("currentUser");


        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("pageNo", pageNo);
        queryParam.put("keyword", keyword);
        PageInfo<Chance> pageInfo = chanceService.chanceList(queryParam, user);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("keyword", keyword);

        model.addAttribute("formWhere", "my");
        return "chance/my_chance";
    }

    /**
     * 新增机会
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/my/new")
    public String myChanceNew(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        List<Customer> custList = customerService.findCustList(user);
        model.addAttribute("custList", custList);
        model.addAttribute("chanceStateList", chanceService.findChanceStateList());

        model.addAttribute("formWhere", "my");
        return "chance/my_chance_new";
    }

    @PostMapping("/my/new")
    @Transactional
    public String myChanceNew(Chance chance, RedirectAttributes redirectAttributes) {
        //新增机会
        chanceService.addNewChance(chance);

        //添加跟进记录
        chanceService.addNewRecored(chance, chance.getCurrentState());

        //对应的客户更新最新跟踪日期
        Customer customer = customerService.findById(chance.getCustId());
        customerService.update(customer);

        redirectAttributes.addFlashAttribute("message", "新增成功");
        return "redirect:/chance/my";
    }

    /**
     * 我的机会详情
     *
     * @param model
     * @return
     */
    @GetMapping("/my/{id:\\d+}")
    public String chanceInfo(Model model, @PathVariable Integer id,HttpSession session) {

        User user = (User) session.getAttribute("currentUser");
        //获取机会详情
        Chance chance = chanceService.findChanceById(id);
        //获取关联客户详情
        Customer customer = customerService.findById(chance.getCustId());
        //获取对应跟进记录
        List<ChanceRecod> recordList = chanceService.findRecod(id);

        //获取当前客户对应的客户任务
        List<Task> taskList = taskService.selectMyTask(user);

        //TODO 查询日程安排
        //TODO 查询相关资料

        model.addAttribute("chance", chance);
        model.addAttribute("customer", customer);
        model.addAttribute("recordList", recordList);
        model.addAttribute("chanceStateList", chanceService.findChanceStateList());
        model.addAttribute("taskList",taskList);
        model.addAttribute("formWhere", "my");
        return "/chance/chance_info";
    }

    /**
     * 更新机会的当前进度
     */
    @PostMapping("/my/{id:\\d+}/state")
    @Transactional
    public String updateCurrentState(@PathVariable Integer id, Integer custId, String currentState, Integer userId, RedirectAttributes redirectAttributes) {

        User user = userService.findByUserId(userId);
        Chance chance = chanceService.findChanceById(id);
        Customer customer = customerService.findById(custId);

        //判断是否符合修改数据的条件
        if (user == null) {
            throw new NotFoundException();
        } else {
            if (!chance.getUserId().equals(userId)) {
                throw new UnableException();
            }
        }

        //更新机会进度
        chanceService.updateChange(chance, currentState);

        //增加对应跟进记录
        chanceService.addNewRecored(chance, currentState);

        //更新客户最新跟踪时间
        customerService.updateLastConcatTime(customer, new Date());

        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/chance/my/" + id;
    }

    /**
     * 增加跟进记录：record
     */
    @PostMapping("/my/new/record")
    @Transactional
    public String newRecord(Integer id, Integer custId, String text, RedirectAttributes redirectAttributes,HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Chance chance = chanceService.findChanceById(id);
        Customer customer = customerService.findById(custId);

        //判断是否符合修改数据的条件
        if (user == null) {
            throw new NotFoundException();
        } else {
            if (!chance.getUserId().equals(user.getId())) {
                throw new UnableException();
            }
        }

        //增加对应跟进记录
        chanceService.addNewRecored(chance, text);
        //更新客户最新跟踪时间
        customerService.updateLastConcatTime(customer, new Date());

        redirectAttributes.addFlashAttribute("message", "添加成功");
        return "redirect:/chance/my/" + id;
    }


    /**
     * 删除机会
     */
    @GetMapping("/my/{id:\\d+}/del")
    @Transactional
    public String delChance(@PathVariable Integer id,Integer custId,HttpSession session){

        User user = (User) session.getAttribute("currentUser");
        Chance chance = chanceService.findChanceById(id);

        //判断是否符合修改数据的条件
        if (user == null) {
            throw new NotFoundException();
        } else {
            if (!chance.getUserId().equals(user.getId())) {
                throw new UnableException();
            }
        }

        //删除对应跟进记录
        chanceService.delRecord(id);
        //删除机会
        chanceService.delChance(id);

        return "redirect:/chance/my";
    }

}
