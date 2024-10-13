package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.UserRole;
import com.refanzzzz.tokonyadia.dto.request.UserAccountRequest;
import com.refanzzzz.tokonyadia.dto.response.UserAccountResponse;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.repository.UserAccountRepository;
import com.refanzzzz.tokonyadia.service.UserAccountService;
import com.refanzzzz.tokonyadia.specification.UserAccountSpecification;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserAccountImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public Page<UserAccountResponse> getAll(UserAccountRequest request) {

        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);
        Specification<UserAccount> specification = UserAccountSpecification.getUserAccountSpecification(request);

        Page<UserAccount> userAccounts = userAccountRepository.findAll(specification, pageable);
        return userAccounts.map((this::toUserAccountResponse));
    }

    @Override
    public UserAccountResponse getById(String id) {
        return toUserAccountResponse(getOne(id));
    }

    @Override
    public UserAccountResponse create(UserAccountRequest data) {
        UserAccount userAccount = UserAccount.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .role(UserRole.getDescription(data.getRole()))
                .build();

        UserAccount saved = userAccountRepository.saveAndFlush(userAccount);
        return toUserAccountResponse(saved);
    }

    @Override
    public UserAccount getOne(String id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
    }

    @Override
    public void remove(String id) {
        UserAccount userAccount = getOne(id);
        userAccountRepository.delete(userAccount);
    }

    @Override
    public UserAccountResponse update(String id, UserAccountRequest data) {
        UserAccount userAccount = getOne(id);
        userAccount.setUsername(data.getUsername());
        userAccount.setPassword(data.getPassword());
        userAccount.setRole(UserRole.getDescription(data.getRole()));

        UserAccount updatedUserAccount = userAccountRepository.saveAndFlush(userAccount);
        return toUserAccountResponse(updatedUserAccount);
    }

    private UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }
}
