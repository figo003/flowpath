package com.figo.common.flowable.demo.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * @ClassName UserTaskListener
 * @Description 监听完成事件，在任务完成时触发，(回退以及拒绝均不会触发)
 * @Author figo
 * @Version 1.0
 **/
public class ExecutionTestListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution delegateExecution) {
        String eventName = delegateExecution.getEventName();
//        System.out.println(eventName + "被执行");
    }
}
