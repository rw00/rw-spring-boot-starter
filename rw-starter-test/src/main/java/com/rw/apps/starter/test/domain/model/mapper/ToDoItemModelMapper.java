package com.rw.apps.starter.test.domain.model.mapper;

import com.rw.apps.starter.test.domain.model.ToDoItem;
import com.rw.apps.starter.test.domain.repository.entity.ToDoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ToDoItemModelMapper {
    ToDoEntity toEntity(ToDoItem item);

    ToDoItem toDto(ToDoEntity entity);
}
