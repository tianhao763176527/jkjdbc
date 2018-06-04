package com.yh.task.util;

import com.yh.task.domain.QuartzVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tianhao on 2018/5/17.
 * 实现对任务添加，修改，删除，停止
 */
public class JobAlteration {
    private ApplicationContext ctx = null;
    private  QuartManager quartManager = null;

    public JobAlteration(){
        ctx =  new ClassPathXmlApplicationContext("main/resources/Spring-mvc.xml");
        quartManager = (QuartManager)ctx.getBean("quartzManager");
    }
    public void AddJob(QuartzVo quartzVo){
        quartManager.addJob(quartzVo);
    }

    public void ModifyJobTime(QuartzVo quartzVo){
        quartManager.modifyJobTime(quartzVo);
    }

    public void removeJob(QuartzVo quartzVo){
        quartManager.removeJob(quartzVo);
    }

    public void stopJob(QuartzVo quartzVo){
        quartManager.shutdownJob(quartzVo);
    }

   public void stopJobs(){
        quartManager.shutdownJobs();
   }
}
