package com.figo.common.flowable.demo.config;

import com.google.common.collect.Sets;
import com.figo.common.flowable.utils.DateUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Date;
import java.util.Set;

public class Parameter2DateConverter implements ConditionalGenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        ConvertiblePair converDate = new ConvertiblePair(String.class, Date.class);
        ConvertiblePair converSqlDate = new ConvertiblePair(String.class, java.sql.Date.class);
        ConvertiblePair converSqlTime = new ConvertiblePair(String.class, java.sql.Time.class);
        ConvertiblePair converTimestamp = new ConvertiblePair(String.class, java.sql.Timestamp.class);
        return Sets.newHashSet(converDate, converSqlDate, converSqlTime, converTimestamp);
    }

    @Override
    @SneakyThrows
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String string = null;
        if (source != null) {
            string = source.toString();
        }
        if (StringUtils.isBlank(string)) {
            return null;
        }

        Date date = DateUtils.parse(string);
        if (java.sql.Date.class.equals(targetType.getType())) {
            return new java.sql.Date(date.getTime());
        } else if (java.sql.Time.class.equals(targetType.getType())) {
            return new java.sql.Time(date.getTime());
        } else if (java.sql.Timestamp.class.equals(targetType.getType())) {
            return new java.sql.Timestamp(date.getTime());
        }
        return date;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return String.class.isAssignableFrom(sourceType.getType()) && Date.class.isAssignableFrom(targetType.getType());
    }
}
