package com.carlwu.minipacs.tools;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzUtil {
    private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";// 任务组
    private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";// 触发器组

    /**
     * 添加任务的方法
     *
     * @param jobName     任务名
     * @param triggerName 触发器名
     * @param jobClass    执行任务的类
     * @param seconds     间隔时间
     * @throws SchedulerException
     */
    public static void addJob(String jobName, String triggerName, Class<? extends Job> jobClass, int seconds)
            throws SchedulerException {

        // 创建一个SchedulerFactory工厂实例
        SchedulerFactory sf = new StdSchedulerFactory();
        // 通过SchedulerFactory构建Scheduler对象
        Scheduler sche = sf.getScheduler();
        // 用于描述Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
        // 构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(triggerName, TRIGGER_GROUP_NAME)// 给触发器起一个名字和组名
                .startNow()// 立即执行
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(seconds)// 时间间隔                                                              // 单位：秒
                                .repeatForever()// 一直执行
                ).build();// 产生触发器

//
//        Trigger trigger = TriggerBuilder
//                .newTrigger()
//                .withIdentity("trigger", "group")
//                .startNow().withSchedule(
//                        CronScheduleBuilder
//                                .cronSchedule("1-30 * * * * ?")
//                ).build();


        //绑定触发器和任务
        sche.scheduleJob(jobDetail, trigger);
        // 启动
        sche.start();
    }

    public static void main(String[] args) {
        try {
            // 添加第一个任务 每隔10秒执行一次
            QuartzUtil.addJob("job1", "trigger1", CheckDcmJob.class, 10);

            // 添加第二个任务 每隔20秒执行一次
           // QuartzUtil.addJob("Job2", "trigger2", InvokeStatJob.class, 20);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
