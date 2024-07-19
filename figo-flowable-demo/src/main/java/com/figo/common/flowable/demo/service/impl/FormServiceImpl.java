package com.figo.common.flowable.demo.service.impl;

import com.figo.common.flowable.demo.entry.FormRes;
import com.figo.common.flowable.demo.mapper.FormMapper;
import com.figo.common.flowable.demo.service.IFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName FormServiceImpl
 * @Version 1.0
 **/
@Service
@Slf4j
public class FormServiceImpl implements IFormService {

    @Resource
    private FormMapper formMapper;


    @Override
    public void insert(FormRes form) {
        formMapper.insert(form);
    }

    @Override
    public int update(FormRes form) {
        return formMapper.modify(form);
    }

    @Override
    public int recall(Integer id) {
        return formMapper.recall(id);
    }

    @Override
    public List<FormRes> findList() {
        return formMapper.findAllForm();
    }

    @Override
    public FormRes get(Integer id) {
        return formMapper.getForm(id);
    }

    @Override
    public int del(Integer id) {
        return formMapper.delete(id);
    }

    @Override
    public int complete(Integer id) {
        return formMapper.complete(id);
    }
}
