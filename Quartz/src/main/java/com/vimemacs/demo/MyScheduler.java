package com.vimemacs.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author HWD
 * @date 2021/3/4 16:39
 */
public class MyScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建调度器 Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 创建 JobDetail 实例，并与 PrintWordsJob 类绑定(Job 执行内容)
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .withIdentity("job1", "group1").build();
        // 创建 Trigger 实例，每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule() .withIntervalInSeconds(1) .repeatForever())
                .build();
        // 执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("-------- Scheduler start ! ---------");
        scheduler.start();

        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("-------- Scheduler shutdown ! ---------");
    }
}
