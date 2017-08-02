package com.kaishengit.quartz.jobs;

import com.kaishengit.weixin.WeiXinUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class weixinRingJob implements Job{

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        Integer toUser = (Integer)dataMap.get("to");
        String message = (String) dataMap.get("message");
        System.out.println("（动态任务）给："+toUser+"，发送微信通知："+message);
        //发微信消息给个人
        weiXinUtil.sendTextMessageToUser("(个人消息)新建个人任务"+message+"，","ShiLei");
    }
}
