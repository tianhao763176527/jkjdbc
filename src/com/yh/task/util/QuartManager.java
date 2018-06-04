package com.yh.task.util;

import com.yh.task.domain.QuartzVo;
import org.quartz.*;
public class QuartManager {
    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addJob(QuartzVo quartzVo){
        String jobName = quartzVo.getJobName();
        String jobGroupName = quartzVo.getJobGroupName();
        String triggerName = quartzVo.getTriggerName();
        String triggerGroupName = quartzVo.getTriggerGroupName();
        String cron = quartzVo.getCron();
        Class jobClass = quartzVo.getJobClass();
        try{
            //任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName,jobGroupName).build();
            //触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            //触发器名，触发器组
            triggerBuilder.withIdentity(triggerName,triggerGroupName);
            triggerBuilder.startNow();
            //触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            //创建Trigger对象
            CronTrigger trigger = (CronTrigger)triggerBuilder.build();
            //调度容器设置JobDetail和trigger
            scheduler.scheduleJob(jobDetail,trigger);
            //启动
            if(scheduler.isShutdown()){
                scheduler.start();
            }
        }catch (Exception e){
           throw  new RuntimeException(e);
        }
    }

    public void modifyJobTime(QuartzVo quartzVo){
        String jobName = quartzVo.getJobName();
        String jobGroupName = quartzVo.getJobGroupName();
        String triggerName = quartzVo.getTriggerName();
        String triggerGroupName = quartzVo.getTriggerGroupName();
        String cron = quartzVo.getCron();
        Class jobClass = quartzVo.getJobClass();
        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if(trigger == null){
                return;
            }
            String oldTime = trigger.getCronExpression();
            if(!oldTime.equalsIgnoreCase(cron)){
                /**
                 * 方式一：调用reschedualeJob修改
                 */
                //触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                //触发器名，触发器组
                triggerBuilder.withIdentity(triggerName,triggerGroupName);
                triggerBuilder.startNow();
                //触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                //创建trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey,trigger);
                /**
                 * 方式二：删除job，重新创建
                 */
                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
            }
        }catch (Exception e){
           throw  new RuntimeException(e);
        }
    }

    //移除一个任务
    public void removeJob(QuartzVo quartzVo){
        String jobName = quartzVo.getJobName();
        String jobGroupName = quartzVo.getJobGroupName();
        String triggerName = quartzVo.getTriggerName();
        String triggerGroupName = quartzVo.getTriggerGroupName();
        String cron = quartzVo.getCron();
        Class jobClass = quartzVo.getJobClass();
        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,triggerGroupName);
            scheduler.pauseTrigger(triggerKey);//停止触发器
            scheduler.unscheduleJob(triggerKey);//移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName,jobGroupName));//删除任务
        }catch (Exception e){
           throw  new RuntimeException(e);
        }
    }
    //停止一个定时任务
    public void shutdownJob(QuartzVo quartzVo){
        String jobName = quartzVo.getJobName();
        String jobGroupName = quartzVo.getJobGroupName();
        String triggerName = quartzVo.getTriggerName();
        String triggerGroupName = quartzVo.getTriggerGroupName();
        String cron = quartzVo.getCron();
        Class jobClass = quartzVo.getJobClass();
        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,triggerGroupName);
            scheduler.pauseTrigger(triggerKey);//停止触发器
            scheduler.pauseJob(JobKey.jobKey(jobName,jobGroupName));
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
    //启动所有定时任务
    public void startJobs(){
        try {
            scheduler.start();
        }catch (Exception e){
           throw  new RuntimeException(e);
        }
    }
    //关闭所有定时任务
    public void shutdownJobs(){
        try {
            if(!scheduler.isShutdown()){
                scheduler.shutdown();
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}
