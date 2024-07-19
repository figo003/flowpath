package com.figo.common.flowable.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class CommonObjectSerializer extends JsonSerializer<Object> {

	private final int maxlen;

	public CommonObjectSerializer(int maxlen) {
		this.maxlen = maxlen;
	}

	@Override
	@SneakyThrows
	public void serialize(Object value, JsonGenerator generator, SerializerProvider serializers) {
		if (value == null) {
			generator.writeNull();
		} else if (value instanceof Collection) {
			generator.writeStartArray();
			for (Object obj : (Collection<?>) value) {
				this.objectWrite(obj, generator);
			}
			generator.writeEndArray();
		} else if (BeanUtils.isSimpleProperty(value.getClass())) {
			generator.writeObject(value);
		} else if (value.getClass().isArray()) {
			generator.writeStartArray();
			for (Object obj : (Object[]) value) {
				this.objectWrite(obj, generator);
			}
			generator.writeEndArray();
		} else {
			this.objectWrite(value, generator);
		}
	}

	private void objectWrite(Object value, JsonGenerator generator) throws Exception {
		Method method;
		Class<?> returnType;
		generator.writeStartObject();
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(value.getClass());
		for (PropertyDescriptor descriptor : descriptors) {
			method = descriptor.getReadMethod();
			returnType = method.getReturnType();
			if (!BeanUtils.isSimpleProperty(method.getReturnType())) {
				continue;
			}
			Object invoke = method.invoke(value);
			generator.writeFieldName(descriptor.getName());
			if (invoke != null && this.maxlen > 0) {
				if (CharSequence.class.isAssignableFrom(returnType)) {
					invoke = StringUtils.substring(invoke.toString(), 0, this.maxlen);
				} else if (returnType.isArray()) {
					returnType = returnType.getComponentType();
					if (returnType == Boolean.TYPE) {
						invoke = Arrays.copyOfRange((boolean[]) invoke, 0, this.maxlen);
					} else if (returnType == Byte.TYPE) {
						invoke = Arrays.copyOfRange((byte[]) invoke, 0, this.maxlen);
					} else if (returnType == Character.TYPE) {
						invoke = Arrays.copyOfRange((char[]) invoke, 0, this.maxlen);
					} else if (returnType == Double.TYPE) {
						invoke = Arrays.copyOfRange((double[]) invoke, 0, this.maxlen);
					} else if (returnType == Float.TYPE) {
						invoke = Arrays.copyOfRange((float[]) invoke, 0, this.maxlen);
					} else if (returnType == Integer.TYPE) {
						invoke = Arrays.copyOfRange((int[]) invoke, 0, this.maxlen);
					} else if (returnType == Long.TYPE) {
						invoke = Arrays.copyOfRange((long[]) invoke, 0, this.maxlen);
					} else if (returnType == Short.TYPE) {
						invoke = Arrays.copyOfRange((short[]) invoke, 0, this.maxlen);
					} else if (CharSequence.class.isAssignableFrom(returnType.getComponentType())) {
						invoke = Arrays.stream((CharSequence[]) invoke).limit(this.maxlen)
							.map(cs -> StringUtils.substring(cs.toString(), 0, this.maxlen));
					} else {
						invoke = Arrays.copyOfRange((Object[]) invoke, 0, this.maxlen);
					}
				}
			}
			generator.writeObject(invoke);
		}
		generator.writeEndObject();
	}
}
