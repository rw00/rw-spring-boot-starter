package com.rw.apps.starter.accounts.repository.entity.converter;

import com.rw.apps.starter.accounts.model.Authorities;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public class StringToRolesSetConverter extends AbstractSetEnumConverter<Authorities> {
    private static final Logger log = LoggerFactory.getLogger(StringToRolesSetConverter.class);

    public StringToRolesSetConverter() {
        super(Authorities.class);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
