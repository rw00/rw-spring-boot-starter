package com.rw.apps.starter.admin.model.mapper;

import com.rw.apps.starter.accounts.model.reqres.AccountEntityView;
import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BaseAccountModelMapper {
    AccountEntityView entityToView(BaseAccountEntity accountEntity);
}
