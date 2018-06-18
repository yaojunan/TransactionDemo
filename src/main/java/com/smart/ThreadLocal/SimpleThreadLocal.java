package com.smart.ThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单模拟ThreadLocal实现过程
 */
public class SimpleThreadLocal {

    private Map valueMap = Collections.synchronizedMap(new HashMap());

    //键为线程对象，值为本线程的变量副本
    public void set(Object newValue){
        valueMap.put(Thread.currentThread() , newValue);
    }

    public Object get(){
        Thread currentThread = Thread.currentThread();
        Object o = valueMap.get(currentThread);
        if(o == null && !valueMap.containsKey(currentThread)){
            o = initialValue();
            valueMap.put(currentThread , o);
        }
        return o;
    }

    public void remove(){
        valueMap.remove(Thread.currentThread());
    }

    public Object initialValue(){
        return null;
    }
}
