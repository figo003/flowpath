package com.figo.common.flowable.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Configuration()
public class JacksonConfiguration {

    @Bean
    public JacksonMapperBuilder jacksonBuilderCustomizer() {
        return new JacksonMapperBuilder();
    }

    static class JacksonMapperBuilder implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

        @Override
        public void customize(Jackson2ObjectMapperBuilder builder) {
            builder.timeZone(TimeZone.getDefault());
            builder.dateFormat(new JacksonDateFormat());
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.serializerByType(MultipartFile.class, new CommonObjectSerializer(-1));
        }

        @Override
        public int getOrder() {
            return Integer.MIN_VALUE;
        }
    }

    static class JacksonDateFormat extends DateFormat {

        private SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        private SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JacksonDateFormat() {
            this.calendar = Calendar.getInstance();
            this.numberFormat = NumberFormat.getNumberInstance();
        }

        @Override
        public StringBuffer format(Date date, StringBuffer append, FieldPosition fpos) {
            if (date instanceof java.sql.Time) {
                return this.formatTime.format(date, append, fpos);
            } else if (date instanceof java.sql.Date) {
                return this.formatDate.format(date, append, fpos);
            }
            return this.formatTimestamp.format(date, append, fpos);
        }

        @Override
        public Date parse(String source, ParsePosition pos) {
            if (StringUtils.isBlank(source)) {
                return null;
            } else if (source.length() == 8) {
                return this.formatTime.parse(source, pos);
            } else if (source.length() == 10) {
                return this.formatDate.parse(source, pos);
            }
            return this.formatTimestamp.parse(source, pos);
        }

        @Override
        public Object clone() {
            JacksonDateFormat other = (JacksonDateFormat) super.clone();
            other.formatTime = (SimpleDateFormat) this.formatTime.clone();
            other.formatDate = (SimpleDateFormat) this.formatDate.clone();
            other.formatTimestamp = (SimpleDateFormat) this.formatTimestamp.clone();
            return other;
        }
    }
}
