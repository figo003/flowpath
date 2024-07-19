package com.figo.common.flowable.demo.mapper;

import com.figo.common.flowable.model.res.GroupRes;
import com.figo.common.flowable.model.res.RoleRes;
import com.figo.common.flowable.model.res.InfoRes;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName OtherMapper
 * @Description 其他杂七杂八非需要mapper
 * @Author figo
 * @Version 1.0
 **/
public interface OtherMapper {

    @Select("SELECT\n" +
            "\tt1.id AS `key`,\n" +
            "\tt1.`name`,\n" +
            "\tt3.id roleKey,\n" +
            "\tt3.`name` roleName,\n" +
            "\tt5.id groupKey,\n" +
            "\tt5.`name` groupName \n" +
            "FROM\n" +
            "\tt_user t1\n" +
            "\tINNER JOIN t_user_role t2 ON t1.id = t2.user_id\n" +
            "\tINNER JOIN t_role t3 ON t3.id = t2.role_id\n" +
            "\tINNER JOIN t_user_group t4 ON t4.user_id = t1.id\n" +
            "\tINNER JOIN t_group t5 ON t5.id = t4.group_id")
    List<InfoRes> findAllUser();

    @Select("select id as `key`,name from t_role")
    List<InfoRes> findAllRole();

    @Select("select id as `key`,name from t_group")
    List<InfoRes> findAllGroup();

}
