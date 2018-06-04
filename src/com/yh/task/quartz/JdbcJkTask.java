package com.yh.task.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tianhao on 2018/5/17.
 */
@Component
public class JdbcJkTask {

    /**
     * 定时
     */
    @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
    public void taskCycle() {
        System.out.println("使用SpringMVC框架配置定时任务");
    }
}
