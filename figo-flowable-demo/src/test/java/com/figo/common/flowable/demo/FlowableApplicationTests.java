package com.figo.common.flowable.demo;

import com.figo.common.flowable.demo.entry.FormRes;
import com.figo.common.flowable.demo.service.impl.FormServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FlowableApplicationTests {

    @Autowired
    FormServiceImpl formService;

    @Test
    void contextLoads() {
        FormRes res = new FormRes();
        res.setDay(1);
        res.setReason("请假啊");
        res.setModelKey("key1111");
        res.setName("zhangsan");
        formService.insert(res);
    }

    @Test
    void contextLoads1() {
        formService.recall(1);
    }


    @Test
    void contextLoads2() {
        FormRes res = new FormRes();
        res.setId(1);
        res.setDay(2);
        res.setReason("请假啊2");
        res.setModelKey("key11112");
        res.setName("lisi");
        formService.update(res);
    }

    @Test
    void contextLoads4() {
        List<FormRes> list = formService.findList();
        list.forEach(a->{
            System.out.println(a.getId()+"=="+a.getName()+"==="+a.getDay());
        });
    }


}
