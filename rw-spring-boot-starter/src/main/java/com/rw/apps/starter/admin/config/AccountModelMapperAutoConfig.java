package com.rw.apps.starter.admin.config;

import com.rw.apps.starter.admin.model.mapper.BaseAccountModelMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountModelMapperAutoConfig {
    @Bean
    BaseAccountModelMapper baseAccountModelMapper() {
        return Mappers.getMapper(BaseAccountModelMapper.class);
    }
}
