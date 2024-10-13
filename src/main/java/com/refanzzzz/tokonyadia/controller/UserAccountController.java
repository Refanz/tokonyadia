package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.UserAccountRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.UserAccountResponse;
import com.refanzzzz.tokonyadia.service.UserAccountService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constant.USER_ACCOUNT_API)
@RequiredArgsConstructor
public class UserAccountController implements Controller<CommonResponse<UserAccountResponse>, UserAccountRequest> {

    private final UserAccountService userAccountService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<UserAccountResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sort) {

        UserAccountRequest userAccountRequest = UserAccountRequest.builder()
                .query(query)
                .size(size)
                .sortBy(sort)
                .build();

        Page<UserAccountResponse> userAccountResponses = userAccountService.getAll(userAccountRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "", userAccountResponses);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<UserAccountResponse>> getById(@PathVariable String id) {
        UserAccountResponse userAccountResponse = userAccountService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "", userAccountResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<UserAccountResponse>> create(@RequestBody UserAccountRequest request) {
        UserAccountResponse userAccountResponse = userAccountService.create(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "", userAccountResponse);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<UserAccountResponse>> remove(@PathVariable String id) {
        userAccountService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "", null);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<UserAccountResponse>> update(@PathVariable String id, @RequestBody UserAccountRequest request) {
        UserAccountResponse userAccountResponse = userAccountService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "", userAccountResponse);
    }
}
