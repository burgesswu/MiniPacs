package com.carlwu.minipacs.tools;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class QuartzScheduling {
    public void localFileCheckRun() throws SchedulerException {
        // 创建一个调度器工厂
        SchedulerFactory factory = new StdSchedulerFactory();

        //任务执行时间
        //Date runTime = DateBuilder.evenMinuteDate(new Date());
        Date runTime = DateBuilder.evenSecondDateAfterNow();

        // 新建JobDetail对象并绑定一个任务
        JobDetail jobDetail = JobBuilder.newJob(InvokeStatJob.class)
                .withIdentity("load_job", "load_group")
                .build();
        // 定义调度规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("load_trigger", "load_group")
                //.startNow()//立即执行
                .startAt(new Date())//设置触发开始的时间
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(1)//时间间隔
                                .withRepeatCount(5)//重复次数(n+1),比如这里将执行6次
                ).build();//生成触发器

        // 从工厂获取一个调度器对象
        Scheduler scheduler = factory.getScheduler();
        //绑定触发器和任务
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println(jobDetail.getKey() + " 运行在: " + runTime);
        scheduler.start();
    }


    public static void main(String[] args) {

        QuartzScheduling demo = new QuartzScheduling();
        try {
            demo.localFileCheckRun();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
