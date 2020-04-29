package com.kuaiyou.lucky.common;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * Integer金额字段反序列化
 * {@code 2.15 -> 215 }
 * @author lake.zhang
 *
 */
public class LongAmountDeserializer extends JsonDeserializer<Number>{

	@Override
	public Number deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if(p.getCurrentValue() == null){
			return 0;
		}
		double val = 0.0;
		try{
			val = p.getDoubleValue();
		}catch(Exception ex){
			try {
				String str = p.getValueAsString();
				str = str.replaceAll(",", "");
				val = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				throw new JsonParseException(e.getMessage(), p.getCurrentLocation());
			}
		}
		BigDecimal b1 = BigDecimal.valueOf(val);
		return b1.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
	}


}
