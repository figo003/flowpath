package com.figo.common.flowable.demo.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.figo.common.flowable.constant.ProcessConstants;
import com.figo.common.flowable.demo.mapper.FormMapper;
import org.apache.commons.lang3.StringUtils;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

import java.util.Map;

/**
 * @ClassName UserTaskListener
 * @Description 监听完成事件，在任务完成时触发，(回退以及拒绝均不会触发)
 * @Author figo
 * @Version 1.0
 **/
public class UserTaskListener implements TaskListener {


    @Override
    public void notify(DelegateTask delegateTask) {
        FormMapper fm = SpringUtil.getBean(FormMapper.class);
        // 审核人
        String assignee = delegateTask.getAssignee();
        // 审核事件
        String eventName = delegateTask.getEventName();
        // 审核节点参数
        Map<String, Object> variables = delegateTask.getVariables();
        System.out.println(delegateTask.getId() + "===" + assignee + "===" + eventName);

        //获取业务id
        // 此处可能是加签的任务触发的，此时就不能判断业务逻辑了,此处以Integer.MIN_VALUE为例，就是为了给个默认值，防止与bizId冲突
        Integer bizId = (Integer) variables.getOrDefault(ProcessConstants.PROCESS_BIZ_KEY, Integer.MIN_VALUE);
        Integer bizType = (Integer) variables.getOrDefault(ProcessConstants.PROCESS_BIZ_TYPE, Integer.MIN_VALUE);
        if (bizType == 1 && !StringUtils.startsWith(delegateTask.getId(), ProcessConstants.ADD_SIGN_PREFIX)) {//请假
            fm.complete(bizId);
        }
    }
}
