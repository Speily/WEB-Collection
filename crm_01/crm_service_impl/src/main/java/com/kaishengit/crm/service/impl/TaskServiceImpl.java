package com.kaishengit.crm.service.impl;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.ChanceService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.TaskService;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.quartz.jobs.weixinRingJob;
import com.kaishengit.weixin.WeiXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.quartz.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ChanceService chanceService;
    @Autowired
    private WeiXinUtil weiXinUtil;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 新增任务
     * @param task
     * @param userId
     */
    @Override
    public void newTask(Task task, Integer userId) {
        task.setCreateTime(new Date());
        task.setUserId(userId);
        taskMapper.insertSelective(task);
    }

    /**
     * 新建任务
     * @param task
     */
    @Override
    @Transactional
    public void newTaskSelected(Task task) {

        task.setCreateTime(new Date());
        taskMapper.insertSelective(task);

        //添加动态定时任务
        if(StringUtils.isNotEmpty(task.getRemindTime())){

            //准备触发器
            //自定义规则内容
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("to",task.getUserId());
            dataMap.put("message",task.getName());

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //创建并设置jobDetail详情
            JobDetail jobDetail = JobBuilder.newJob(weixinRingJob.class)
                                    .withIdentity(new JobKey("User:"+task.getUserId(),"weixinGroup"))
                                    .setJobData(dataMap).build();

            //Trigger cron

            //字符串日期格式转换为JodaTime的DateTime类对象
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = format.parseDateTime(task.getRemindTime());
            //根据日期生成cron表达式
            Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                        .withYear(on(dateTime.getYear()))
                    .withMonth(on(dateTime.getMonthOfYear()))
                    .withDoM(on(dateTime.getDayOfMonth()))
                    .withHour(on(dateTime.getHourOfDay()))
                    .withMinute(on(dateTime.getMinuteOfHour()))
                    .withSecond(on(dateTime.getSecondOfMinute()))
                    .withDoW(questionMark())
                    .instance();
            String cronText = cron.asString();
            System.out.println(cronText);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronText);
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            //调用
            try {
                scheduler.scheduleJob(jobDetail,trigger);
                scheduler.start();
            }catch (SchedulerException ex){
                throw  new ServiceException("添加定时任务异常",ex);
            }
        }
    }

    /**
     * 查询我的任务列表（未完成）
     * @param user
     * @return
     */
    @Override
    public List<Task> selectMyTask(User user) {
        Integer doneState = 0;
        return taskMapper.selectByParam(user,doneState);
    }

    /**
     * 我的任务列表（所有）
     * @param user
     * @return
     */
    @Override
    public List<Task> selectMyTaskUndone(User user) {
        Integer state = 1;
        return taskMapper.selectByParam(user,state);
    }

    /**
     * 查询cust对应未完成任务
     * @param customerId
     * @return
     */
    @Override
    public List<Task> selectUndoneTask(Integer customerId,Integer userId) {
        return taskMapper.selectByCust(customerId,userId);
    }

    /**
     * 删除任务
     * @param id
     */
    @Override
    @Transactional
    public void delTask(Integer id) {

        System.out.println(id);
        //如果有提醒，删除绑定的动态定时任务
        Task task = taskMapper.selectById(id);
        System.out.println(task.getName());

        if(StringUtils.isNotEmpty(task.getRemindTime())){
            try {
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                //根据JobKey删除
                scheduler.deleteJob(new JobKey("User:"+task.getUserId(),"weixinGroup"));
            } catch (SchedulerException ex){
                throw new ServiceException("解除定时任务发生异常",ex);
            }

        taskMapper.deleteByPrimaryKey(id);

        }

    }

    /**
     * 改变任务状态
     * @param id
     */
    @Override
    public void doneTask(String currentState,Integer id) {

        Task task = taskMapper.selectByPrimaryKey(id);
        if(currentState.equals("done")){
            task.setState(0);
            taskMapper.updateByPrimaryKeySelective(task);
        }
        if(currentState.equals("undone")){
            task.setState(1);
            taskMapper.updateByPrimaryKeySelective(task);
        }
    }


}
