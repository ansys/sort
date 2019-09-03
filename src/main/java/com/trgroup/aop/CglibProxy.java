package com.trgroup.aop;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
@Slf4j
public class CglibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long starttime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Object result = methodProxy.invokeSuper(o, objects);
        long endtime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info(String.format("time elaspse:%d",endtime-starttime));
        return result;
    }
}
