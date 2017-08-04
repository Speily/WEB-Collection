package com.kaishengit.quartz.jobs;

import com.kaishengit.weixin.WeiXinUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class SpringJob implements Job {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String message = jobDataMap.getString("message");
        System.out.println("（固定任务5s)------>" + message);

        weiXinUtil.sendTextMessageToUser("定时任务5s","ShiLei");

    }
}
