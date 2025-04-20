package com.rw.apps.starter.accounts.repository;

import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface BaseAccountsRepository<T extends BaseAccountEntity> extends JpaRepository<T, String> {
    @Transactional(readOnly = true)
    Optional<T> findByEmail(String email);

    @Transactional(readOnly = true) // TODO: should not use nativeQuery
    @Query(value = "SELECT a.* FROM accounts a WHERE a.authorities LIKE '%ROLE_ADMIN%'", nativeQuery = true)
    List<T> findAllAdmins();
}
