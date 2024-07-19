package com.figo.common.flowable.demo.entry;

import lombok.Data;

/**
 * @ClassName FormRes
 * @Version 1.0
 **/
@Data
public class FormRes {

    private Integer id;

    private String name;

    private Integer day;

    private String reason;

    private String createDate;

    private Integer status;

    private String modelKey;

    private String processInstanceId;
}
