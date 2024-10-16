package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.constant.UserRole;
import com.refanzzzz.tokonyadia.dto.request.UserAccountRequest;
import com.refanzzzz.tokonyadia.dto.response.UserAccountResponse;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.repository.UserAccountRepository;
import com.refanzzzz.tokonyadia.service.UserAccountService;
import com.refanzzzz.tokonyadia.specification.UserAccountSpecification;
import com.refanzzzz.tokonyadia.util.SortUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserAccountImpl implements UserAccountService {

    @Value("${tokonyadia.user-admin}")
    private String USERNAME_ADMIN;

    @Value("${tokonyadia.user-password}")
    private String PASSWORD_ADMIN;

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUser() {
        boolean exist = userAccountRepository.existsUserAccountByUsername(USERNAME_ADMIN);
        if (exist) return;

        UserAccount userAccount = UserAccount.builder()
                .username(USERNAME_ADMIN)
                .password(passwordEncoder.encode(PASSWORD_ADMIN))
                .role(UserRole.ROLE_ADMIN)
                .build();

        userAccountRepository.saveAndFlush(userAccount);
    }

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAccountResponse create(UserAccountRequest data) {
        try {

            UserRole userRole = UserRole.getDescription(data.getRole());

            if (userRole == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");

            UserAccount userAccount = UserAccount.builder()
                    .username(data.getUsername())
                    .password(passwordEncoder.encode(data.getPassword()))
                    .role(userRole)
                    .build();

            UserAccount saved = userAccountRepository.saveAndFlush(userAccount);
            return toUserAccountResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Constant.ERROR_USERNAME_DUPLICATE);
        }
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findUserAccountsByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }
}
