package com.rw.apps.starter.accounts.repository.entity.converter;

import com.rw.apps.starter.accounts.model.AccountProvider;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public class StringToAccountProvidersSetConverter extends AbstractSetEnumConverter<AccountProvider> {
    private static final Logger log = LoggerFactory.getLogger(StringToAccountProvidersSetConverter.class);

    public StringToAccountProvidersSetConverter() {
        super(AccountProvider.class);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
