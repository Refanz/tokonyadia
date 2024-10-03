package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.TransactionResponse;
import com.refanzzzz.tokonyadia.service.TransactionService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.TRANSACTION_URL)
@AllArgsConstructor
public class TransactionController implements Controller<CommonResponse<TransactionResponse>, TransactionRequest> {

    private TransactionService transactionService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<TransactionResponse>> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false) String sort) {

        Page<TransactionResponse> transactionResponsePage = transactionService.getAll(page, size, sort);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all transaction", transactionResponsePage);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<TransactionResponse>> getById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get transaction", transactionResponse);
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionResponse>> insert(TransactionRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionResponse>> remove(String id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionResponse>> update(String id, TransactionRequest request) {
        return null;
    }
}
