package com.rw.apps.starter.test.domain.api;

import com.rw.apps.starter.common.exceptions.InvalidRequestException;
import com.rw.apps.starter.common.security.model.CurrentUser;
import com.rw.apps.starter.test.domain.model.ToDoItem;
import com.rw.apps.starter.test.domain.service.ToDoItemsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
class ToDoItemsApiV1 {
    private final ToDoItemsService toDoItemsService;

    @GetMapping
    List<ToDoItem> getAll(@AuthenticationPrincipal CurrentUser user) {
        return toDoItemsService.getAll(user.getUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ToDoItem create(@AuthenticationPrincipal CurrentUser user, @RequestBody @Valid ToDoItem item) {
        return toDoItemsService.create(user.getUserId(), item);
    }

    @PutMapping("{id}")
    ToDoItem update(@AuthenticationPrincipal CurrentUser user, @PathVariable @NotBlank String id, @RequestBody @Valid ToDoItem item) {
        if (!id.equals(item.id())) {
            throw new InvalidRequestException("IDs don't match");
        }
        return toDoItemsService.update(user.getUserId(), item);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@AuthenticationPrincipal CurrentUser user, @PathVariable @NotBlank String id) {
        toDoItemsService.delete(user.getUserId(), id);
    }
}
