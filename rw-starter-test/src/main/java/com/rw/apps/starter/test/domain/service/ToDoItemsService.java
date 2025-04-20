package com.rw.apps.starter.test.domain.service;

import com.rw.apps.starter.common.exceptions.InvalidRequestException;
import com.rw.apps.starter.test.domain.model.ToDoItem;
import com.rw.apps.starter.test.domain.model.mapper.ToDoItemModelMapper;
import com.rw.apps.starter.test.domain.repository.ToDoItemsRepository;
import com.rw.apps.starter.test.domain.repository.entity.ToDoEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ToDoItemsService {
    private final ToDoItemsRepository itemsRepository;
    private final ToDoItemModelMapper itemModelMapper;

    @Transactional(readOnly = true)
    public List<ToDoItem> getAll(String userId) {
        return itemsRepository.findAllByUserId(userId)
                              .stream()
                              .map(itemModelMapper::toDto)
                              .toList();
    }

    @Transactional
    public ToDoItem create(String userId, ToDoItem item) {
        if (itemsRepository.existsById(item.id())) {
            throw new InvalidRequestException("Item with ID " + item.id() + " already exists");
        }
        ToDoEntity entity = itemModelMapper.toEntity(item);
        entity.setUserId(userId);
        ToDoEntity savedEntity = itemsRepository.save(entity);
        return itemModelMapper.toDto(savedEntity);
    }

    @Transactional
    public ToDoItem update(String userId, ToDoItem item) {
        ToDoEntity existingEntity = checkOwnership(userId, item.id(), true);
        existingEntity.setTitle(item.title());
        existingEntity.setDescription(item.description());
        existingEntity.setStatus(item.status());
        ToDoEntity savedEntity = itemsRepository.save(existingEntity);
        return itemModelMapper.toDto(savedEntity);
    }

    @Transactional
    public void delete(String userId, String id) {
        checkOwnership(userId, id, false);
        itemsRepository.deleteById(id);
    }

    private ToDoEntity checkOwnership(String userId, String id, boolean checkExistence) {
        ToDoEntity existingEntity = itemsRepository.findById(id)
                                                   .orElse(null);
        if (existingEntity == null && checkExistence) {
            throw new InvalidRequestException("Item with ID " + id + " does not exist");
        }
        if (existingEntity != null && !userId.equals(existingEntity.getUserId())) {
            throw new InvalidRequestException("Item with ID " + id + " does not belong to user " + userId);
        }
        return existingEntity;
    }
}
