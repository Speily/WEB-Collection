package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;

import java.util.List;

public interface TaskService {

    void newTask(Task task, Integer userId);

    void newTaskSelected(Task task);

    List<Task> selectMyTask(User user);

    void delTask(Integer id);

    void doneTask(String currentState,Integer id);

    List<Task> selectMyTaskUndone(User user);

    List<Task> selectUndoneTask(Integer customerId,Integer userId);
}
