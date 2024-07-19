package com.figo.common.flowable.demo.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import com.figo.common.flowable.constant.ProcessConstants;
import com.figo.common.flowable.demo.mapper.FormMapper;
import com.figo.common.flowable.demo.mapper.OtherMapper;
import com.figo.common.flowable.exception.GkswException;
import com.figo.common.flowable.model.res.InfoRes;
import com.figo.common.flowable.service.IFlowBaseService;
import com.figo.common.flowable.service.IFlowBusinessService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName BusinessServiceImpl
 * @Description 业务实现逻辑
 * @Author figo
 * @Version 1.0
 **/
@Service
public class BusinessServiceImpl implements IFlowBusinessService, IFlowBaseService {

    @Resource
    OtherMapper otherMapper;

    //获取审核人列表
    @Override
    public List<InfoRes> businessUserList(Integer type) {
        if (type == null) {
            return new ArrayList<>();
        }
        if (type == 1) {
            return otherMapper.findAllUser();
        } else if (type == 2) {
            return otherMapper.findAllRole();
        } else if (type == 3) {
            return otherMapper.findAllGroup();
        } else {
            throw new GkswException("未找到相关类型");
        }

    }

    @Override
    public List<InfoRes> findUserByKeyAndType(List<String> keys, Integer type) {
        if (type == null) {
            return new ArrayList<>();
        }
        List<InfoRes> allUser = otherMapper.findAllUser();
        // 1表示用户，2表示根据角色筛选；3根据组筛选
        if (type == 1) {
            allUser = allUser.stream().filter(d -> keys.contains(d.getKey())).collect(Collectors.toList());
        } else if (type == 2) {
            allUser =
                    allUser.stream().filter(d -> d.getRoleKey() != null && keys.contains(d.getRoleKey())).collect(Collectors.toList());
        } else if (type == 3) {
            allUser =
                    allUser.stream().filter(d -> d.getGroupKey() != null && keys.contains(d.getGroupKey())).collect(Collectors.toList());
        } else {
            throw new GkswException("未找到相关类型");
        }
        return allUser;
    }

    @Override
    public List<JSONObject> findBizDict() {
        List<JSONObject> dicts = new ArrayList<>();
        JSONObject item = new JSONObject();
        item.put("id", 1);
        item.put("name", "审核");
        dicts.add(item);

        JSONObject item1 = new JSONObject();
        item1.put("id", 2);
        item1.put("name", "抄送");
        dicts.add(item1);

        return dicts;
    }

    //回退回调接口
    @Override
    public void taskReturn(Task task, Map<String, Object> variables) {

    }

    //终止回调接口
    @Override
    public void stopProcess(Task task, Map<String, Object> variables) {
        FormMapper fm = SpringUtil.getBean(FormMapper.class);
        //获取业务id
        Integer bizId = (Integer) variables.get(ProcessConstants.PROCESS_BIZ_KEY);
        Integer bizType = (Integer) variables.get(ProcessConstants.PROCESS_BIZ_TYPE);
        if (bizType == 1) {//请假
            //修改业务状态
            fm.stopProcess(bizId);
        }
    }

}
