package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionResponse;
import com.refanzzzz.tokonyadia.service.TransactionService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.refanzzzz.tokonyadia.constant.Constant.TRANSACTION_API;

@RestController
@RequestMapping(TRANSACTION_API)
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> getAll(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sort) {

        TransactionRequest transactionRequest = TransactionRequest
                .builder()
                .query(query)
                .page(page)
                .size(size)
                .sortBy(sort)
                .build();

        Page<TransactionResponse> transactionResponsePage = transactionService.getAll(transactionRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all transaction", transactionResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TransactionResponse>> getById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get transaction", transactionResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> create(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.createTransaction(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_TRANSACTION, transactionResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/items")
    public ResponseEntity<CommonResponse<TransactionResponse>> addItemTransaction(@PathVariable String id, @RequestBody TransactionDetailRequest request) {
        TransactionResponse transactionResponse = transactionService.addTransactionItem(id, request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, Constant.SUCCESS_ADD_ITEM_TRANSACTION, transactionResponse);
    }
}
