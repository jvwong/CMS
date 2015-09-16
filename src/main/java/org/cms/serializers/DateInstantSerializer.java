package org.cms.serializers;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;



public class DateInstantSerializer extends JsonSerializer<Instant> {
	
    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider arg2)
        throws IOException, JsonProcessingException {

        gen.writeString(value.toString());
    }
}



