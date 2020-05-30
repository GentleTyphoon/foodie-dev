package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author wuweifu
 * @Date 2020/5/30 7:59 下午
 * @Version V1.0
 * @Description:
 **/
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOder() {
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为： " +
                DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}
