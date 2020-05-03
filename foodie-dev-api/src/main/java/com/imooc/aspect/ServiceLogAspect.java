package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author wuweifu
 * @Date 2020/5/3 10:02 下午
 * @Version V1.0
 * @Description:
 **/
@Aspect
@Component
public class ServiceLogAspect {
    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /** 切面表达式
     *
     *  */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("===== 开始执行 {}.{} =====",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName()
        );

        // 开始时间
        long begin = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        //结束时间
        long end = System.currentTimeMillis();

        //时间差
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("===== 执行结束，耗时：{} 毫秒", takeTime);
        } else if (takeTime > 2000) {
            log.warn("===== 执行结束，耗时：{} 毫秒", takeTime);
        } else {
            log.info("===== 执行结束，耗时：{} 毫秒", takeTime);
        }

        return result;
    }

}
