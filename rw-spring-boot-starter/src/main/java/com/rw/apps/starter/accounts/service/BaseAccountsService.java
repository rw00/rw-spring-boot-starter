package com.rw.apps.starter.accounts.service;

import com.rw.apps.starter.accounts.repository.BaseAccountsRepository;
import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseAccountsService<T extends BaseAccountEntity> {
    private final BaseAccountsRepository<T> accountsRepository;

    public BaseAccountsService(BaseAccountsRepository<T> accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Transactional(readOnly = true)
    public T getAccountByEmail(String email) {
        return accountsRepository.findByEmail(email)
                                 .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(String id) {
        return accountsRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAllAdmins() {
        return accountsRepository.findAllAdmins();
    }

    @Transactional(readOnly = true)
    public Page<T> findAllUsers(Pageable pageable) {
        return accountsRepository.findAll(pageable);
    }

    @Transactional
    public T save(T accountEntity) {
        return accountsRepository.save(accountEntity);
    }
}
