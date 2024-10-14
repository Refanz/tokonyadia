package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.UserAccountRequest;
import com.refanzzzz.tokonyadia.dto.response.UserAccountResponse;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends Service<UserAccountResponse, UserAccountRequest, UserAccount>, UserDetailsService {

}
