package com.figo.common.flowable.demo.controller;

import com.figo.common.flowable.constant.ProcessConstants;
import com.figo.common.flowable.demo.entry.FormRes;
import com.figo.common.flowable.demo.service.IFormService;
import com.figo.common.flowable.model.result.AjaxResult;
import com.figo.common.flowable.service.IFlowDefinitionService;
import com.figo.common.flowable.service.IFlowTaskService;
import com.figo.common.flowable.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OtherController
 * @Description 其他demo需要接口
 * @Author figo
 * @Version 1.0
 **/
@Slf4j
@Api(tags = "其他demo需要接口")
@RestController
@RequestMapping("/other")
public class OtherController {


    @Resource
    private IFormService formsService;
    @Autowired
    private IFlowDefinitionService flowDefinitionService;
    @Autowired
    private IFlowTaskService flowTaskService;

    @ApiOperation(value = "查询所有表单提交", response = FormRes.class)
    @PostMapping(value = "/findAllForm")
    public AjaxResult findAllForm() {
        List<FormRes> formList = formsService.findList();
        return AjaxResult.success(formList);
    }

    @ApiOperation(value = "撤回表单")
    @PostMapping(value = "/recall")
    public AjaxResult recall(Integer id) {
        //判断流程是否已经开始审核
        FormRes form = formsService.get(id);
        if (form == null) {
            AjaxResult.error("提交的表单不存在");
        }
        //撤回流程
        try {
            flowTaskService.taskRecall(form.getProcessInstanceId());
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        //修改状态
        formsService.recall(id);
        return AjaxResult.success();
    }

    @ApiOperation(value = "新增表单")
    @PostMapping(value = "/addForm")
    public AjaxResult addForm(@RequestBody FormRes formRes) {
        formsService.insert(formRes);
        //发起流程后的流程实例
        String processInstanceId = null;
        try {
            //发起流程
            processInstanceId = initiate(formRes);
        } catch (Exception e) {
            formsService.del(formRes.getId());
            return AjaxResult.error(e.getMessage());
        }
        //数据入库
        formRes.setProcessInstanceId(processInstanceId);
        formsService.update(formRes);
        return AjaxResult.success();
    }

    @ApiOperation(value = "修改表单")
    @PostMapping(value = "/modifyForm")
    public AjaxResult modifyForm(@RequestBody FormRes formRes) {
        //发起流程后的流程实例
        String processInstanceId = null;
        try {
            //发起流程
            processInstanceId = initiate(formRes);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        //数据入库
        formRes.setProcessInstanceId(processInstanceId);
        formsService.update(formRes);
        return AjaxResult.success();
    }

    //发起流程
    private String initiate(FormRes formRes) {
        //设置启动流程变量
        Map<String, Object> variables = new HashMap<>();
        //设置全局变量，方便流程中使用
        variables.put("day", formRes.getDay());
        //设置title，用于待办已办列表中回显
        variables.put(ProcessConstants.PROCESS_TITLE, formRes.getName() + "发起请假流程-" + DateUtils.parseDate("yyyy-MM-dd HH:mm", new Date()));
        //业务主键，用于审核主数据回显，和业务相关操作
        variables.put(ProcessConstants.PROCESS_BIZ_KEY, formRes.getId());
        //业务类型，用于审核主数据回显，和业务相关操作,1 请假 2 报销
        variables.put(ProcessConstants.PROCESS_BIZ_TYPE, 1);
        //发起人标识
        String initiatorUserKey = "1";
        //设置发起人
        variables.put(ProcessConstants.PROCESS_INITIATOR, initiatorUserKey);
        //发起流程
        AjaxResult rs = flowDefinitionService.startProcessInstanceByKey(formRes.getModelKey(), initiatorUserKey, variables);
        return rs.get("processInstanceId").toString();
    }

}
