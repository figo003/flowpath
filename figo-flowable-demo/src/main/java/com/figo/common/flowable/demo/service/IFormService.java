package com.figo.common.flowable.demo.service;

import com.figo.common.flowable.demo.entry.FormRes;

import java.util.List;

/**
 * @ClassName IFormTaskService
 * @Version 1.0
 **/
public interface IFormService {

    void insert(FormRes form);

    int update(FormRes form);

    int recall(Integer id);

    List<FormRes> findList();

    FormRes get(Integer id);

    int del(Integer id);

    int complete(Integer id);
}
