package com.trgroup.aop;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Slf4j
public class AOPHandler implements InvocationHandler {
    private Object object;

    public AOPHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long starttime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Object ret=method.invoke(object,args);
        long endtime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info(String.format("time elaspse:%d",endtime-starttime));
        return ret;
    }
}
