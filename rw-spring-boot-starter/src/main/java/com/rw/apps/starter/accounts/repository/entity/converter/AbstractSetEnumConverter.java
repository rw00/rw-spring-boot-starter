package com.rw.apps.starter.accounts.repository.entity.converter;

import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractSetEnumConverter<T extends Enum<T>> implements AttributeConverter<Set<T>, String> {
    private static final String DELIMITER = ",";
    private final Class<T> clazz;

    protected AbstractSetEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String convertToDatabaseColumn(Set<T> set) {
        if (CollectionUtils.isEmpty(set)) {
            getLogger().warn("Empty set");
            return "";
        }
        return set.stream()
                  .map(Enum::name)
                  .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<T> convertToEntityAttribute(String str) {
        if (!StringUtils.hasText(str)) {
            getLogger().warn("Empty string");
            return new HashSet<>();
        }
        return Arrays.stream(str.split(DELIMITER))
                     .map(e -> Enum.valueOf(clazz, e))
                     .collect(Collectors.toSet());
    }

    protected abstract Logger getLogger();
}
