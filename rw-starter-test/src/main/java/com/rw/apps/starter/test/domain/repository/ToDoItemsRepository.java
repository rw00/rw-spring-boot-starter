package com.rw.apps.starter.test.domain.repository;

import com.rw.apps.starter.test.domain.repository.entity.ToDoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoItemsRepository extends JpaRepository<ToDoEntity, String> {
    List<ToDoEntity> findAllByUserId(String userId);
}
