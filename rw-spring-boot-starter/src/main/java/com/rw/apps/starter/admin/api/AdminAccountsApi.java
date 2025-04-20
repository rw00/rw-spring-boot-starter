package com.rw.apps.starter.admin.api;

import com.rw.apps.starter.accounts.model.reqres.AccountEntityView;
import com.rw.apps.starter.accounts.repository.entity.BaseAccountEntity;
import com.rw.apps.starter.accounts.service.BaseAccountsService;
import com.rw.apps.starter.admin.model.mapper.BaseAccountModelMapper;
import com.rw.apps.starter.admin.model.reqres.UpdateAccountRequest;
import com.rw.apps.starter.admin.model.reqres.UpdateAccountResponse;
import com.rw.apps.starter.common.api.ApiConstants;
import com.rw.apps.starter.common.api.token.TokenGenerator;
import com.rw.apps.starter.common.exceptions.NotFoundException;
import com.rw.apps.starter.common.security.util.AdminUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/accounts")
@Tag(name = AdminUtils.ADMIN_API_TAG)
class AdminAccountsApi<T extends BaseAccountEntity> {
    private final BaseAccountsService<T> accountsService;
    private final BaseAccountModelMapper accountModelMapper;
    private final TokenGenerator tokenGenerator;

    AdminAccountsApi(BaseAccountsService<T> accountsService, BaseAccountModelMapper baseAccountModelMapper, TokenGenerator tokenGenerator) {
        this.accountsService = accountsService;
        this.accountModelMapper = baseAccountModelMapper;
        this.tokenGenerator = tokenGenerator;
    }

    @GetMapping
    List<AccountEntityView> getUsers(Pageable pageable) {
        return accountsService.findAllUsers(pageable)
                              .stream()
                              .map(accountModelMapper::entityToView)
                              .toList();
    }

    @GetMapping("admins")
    List<AccountEntityView> getAdmins() {
        return accountsService.findAllAdmins()
                              .stream()
                              .map(accountModelMapper::entityToView)
                              .toList();
    }

    @GetMapping(ApiConstants.ID_PATH_PARAM)
    AccountEntityView getAccount(@PathVariable @NotBlank String id) {
        return accountsService.findById(id)
                              .map(accountModelMapper::entityToView)
                              .orElseThrow(NotFoundException::account);
    }

    @PostMapping(ApiConstants.ID_PATH_PARAM)
    UpdateAccountResponse updateAccount(@PathVariable @NotBlank String id, @RequestBody @Valid UpdateAccountRequest updateAccountRequest) {
        T accountEntity = accountsService.findById(id)
                                         .orElseThrow(NotFoundException::account);

        BaseAccountEntity savedAccountEntity = updateAccountStatus(accountEntity, updateAccountRequest);

        return new UpdateAccountResponse(savedAccountEntity.isEnabled(),
                                         savedAccountEntity.getConfirmationToken() == null);
    }

    private T updateAccountStatus(T userEntity, UpdateAccountRequest updateAccountRequest) {
        if (updateAccountRequest.enableAccount() != null) {
            userEntity.setEnabled(updateAccountRequest.enableAccount());
        }
        if (updateAccountRequest.confirmAccount() != null) {
            if (updateAccountRequest.confirmAccount()) {
                userEntity.setConfirmationToken(null);
            } else {
                userEntity.setConfirmationToken(tokenGenerator.generateToken());
                // TODO: send confirmation email
            }
        }
        return accountsService.save(userEntity);
    }
}
