package com.rw.apps.starter.common.security.model.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class AuthoritiesJsonSerializer extends JsonSerializer<Collection<? extends GrantedAuthority>> {
    @Override
    public void serialize(Collection<? extends GrantedAuthority> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (GrantedAuthority grantedAuthority : value) {
            gen.writeString(grantedAuthority.getAuthority());
        }
        gen.writeEndArray();
    }
}
