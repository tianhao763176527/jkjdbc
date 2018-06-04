package com.yh.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;

/**
 * Created by tianhao on 2018/5/17.
 * 使用触发器定时
 */
public class JKJdbcTask implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date() + ": job 1 doing something...");
    }
    public void doSomething(){
        System.out.println(new Date() + ": job 2 doing something...");
    }
}
