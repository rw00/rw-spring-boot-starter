package com.rw.apps.starter.test.domain.model;

import com.rw.apps.starter.common.validation.annotations.NullOrNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ToDoItem(@NotBlank String id,
                       @NotBlank String title,
                       @NullOrNotBlank String description,
                       @NotNull Status status) {
}
