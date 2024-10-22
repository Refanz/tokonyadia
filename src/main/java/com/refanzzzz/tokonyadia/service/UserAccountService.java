package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.auth.UserAccountRequest;
import com.refanzzzz.tokonyadia.dto.response.auth.UserAccountResponse;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends BaseService<UserAccountRequest, UserAccountResponse, UserAccount>, UserDetailsService {
    UserAccount create(UserAccount userAccount);
}
