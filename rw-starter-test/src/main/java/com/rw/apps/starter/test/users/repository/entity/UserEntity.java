package com.rw.apps.starter.test.users.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(nullable = false, unique = true, insertable = false, updatable = false)
    private String id;
    @OneToOne
    private AccountEntity account;
    @Column
    private String firstName;
    @Column
    private String lastName;
}
