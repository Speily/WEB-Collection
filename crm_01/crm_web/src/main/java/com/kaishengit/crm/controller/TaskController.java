package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 任务控制器
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 新增任务
     * @return
     */
    @GetMapping("/new")
    public String newTask(Model model){
        model.addAttribute("formWhere","my");
        return "task/task_new";
    }
    @PostMapping("/new")
    public String newTask(Task task,RedirectAttributes redirectAttributes){
        taskService.newTaskSelected(task);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/task/my";
    }

    /**
     * 我的任务列表(未完成)
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String taskListUndone(HttpSession session, Model model){
        User user = (User) session.getAttribute("currentUser");
        List<Task> taskList = taskService.selectMyTask(user);
        model.addAttribute("taskList",taskList);
        return "task/my_task";
    }
    /**
     * 我的任务列表(全部)
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my/all")
    public String taskListAll(HttpSession session, Model model){
        User user = (User) session.getAttribute("currentUser");
        List<Task> taskList = taskService.selectMyTaskUndone(user);
        model.addAttribute("taskList",taskList);
        model.addAttribute("dataFrom","all");
        return "task/my_task";
    }
    /**
     * 改变任务状态
     */
    @GetMapping("/{currentState}/{id:\\d+}")
    public String doneTask(@PathVariable(value = "currentState") String currentState,@PathVariable Integer id){
        taskService.doneTask(currentState,id);
        return "redirect:/task/my";
    }
    /**
     * 删除任务
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/del/{id:\\d+}")
    public String delTask(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        taskService.delTask(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/task/my";
    }
}
