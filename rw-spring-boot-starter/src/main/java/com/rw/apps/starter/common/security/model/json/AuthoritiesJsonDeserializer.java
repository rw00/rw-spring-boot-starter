package com.rw.apps.starter.common.security.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rw.apps.starter.accounts.model.Authorities;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.data.util.StreamUtils;
import org.springframework.security.core.GrantedAuthority;

public class AuthoritiesJsonDeserializer extends JsonDeserializer<Collection<? extends GrantedAuthority>> {
    @Override
    public Collection<? extends GrantedAuthority> deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        return StreamUtils.createStreamFromIterator(node.elements())
                          .map(JsonNode::asText)
                          .sorted()
                          .map(Authorities::valueOf)
                          .collect(Collectors.toSet());
    }
}
