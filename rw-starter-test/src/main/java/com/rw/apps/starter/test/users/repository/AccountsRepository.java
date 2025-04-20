package com.rw.apps.starter.test.users.repository;

import com.rw.apps.starter.accounts.repository.BaseAccountsRepository;
import com.rw.apps.starter.test.users.repository.entity.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends BaseAccountsRepository<AccountEntity> {
}
