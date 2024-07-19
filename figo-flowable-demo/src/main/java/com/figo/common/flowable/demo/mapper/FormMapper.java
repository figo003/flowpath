package com.figo.common.flowable.demo.mapper;

import com.figo.common.flowable.demo.entry.FormRes;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName FormMapper
 * @Version 1.0
 **/
public interface FormMapper {

    @Select("select * from t_form order by create_date desc")
    List<FormRes> findAllForm();

    @Select("select * from t_form where id = #{id}")
    FormRes getForm(Integer id);

    @Insert("INSERT INTO t_form (name, model_key, day, reason,process_instance_id) " +
            " VALUES (#{name}, #{modelKey}, #{day}, #{reason}, #{processInstanceId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(FormRes form);

    @Update("update t_form set name = #{name},model_key = #{modelKey},day=#{day},reason=#{reason},status=0,process_instance_id=#{processInstanceId} where id = #{id}")
    int modify(FormRes form);

    @Update("update t_form set status=1 where id = #{id}")
    int recall(Integer id);

    @Delete("delete from t_form where id = #{id}")
    int delete(Integer id);

    @Update("update t_form set status=2 where id = #{id}")
    int complete(Integer id);

    @Update("update t_form set status=3 where id = #{id}")
    int stopProcess(Integer id);
}
