package com.vimemacs.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author HWD
 * @date 2021/3/4 15:06
 */
public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException {
        // 创建一个 Scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");

        // 创建一个 Job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .usingJobData("j1", "jv1")
                .withIdentity("myjob", "mygroup")
                .build();
        job.getJobDataMap().put("j2", "jv2");

        // 创建一个 Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever())
                .build();
        trigger.getJobDataMap().put("t2", "tv2");

        // 注册 trigger 并启动 scheduler
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
