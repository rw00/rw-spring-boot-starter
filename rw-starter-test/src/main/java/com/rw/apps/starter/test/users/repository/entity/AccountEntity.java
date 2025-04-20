package com.rw.apps.starter.test.users.repository.entity;

import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseAccountEntity {
}
