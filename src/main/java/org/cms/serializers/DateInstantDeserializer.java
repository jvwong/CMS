package org.cms.serializers;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class DateInstantDeserializer extends JsonDeserializer<Instant> {

	@Override
	public Instant deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
	    return Instant.parse(arg0.getText());
	}
}

