package com.kuaiyou.lucky.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 金额字段序列化 {@code 123 -> 1.23}
 * 
 * @see JsonSerializer
 * @author lake.zhang
 *
 */
public class AmountSerializer extends JsonSerializer<Number> {

	@Override
	public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		try {
			gen.writeNumber(value.longValue() / 100.0);
		} catch (Exception e) {
			throw new JsonGenerationException(e, gen);
		}
	}

}