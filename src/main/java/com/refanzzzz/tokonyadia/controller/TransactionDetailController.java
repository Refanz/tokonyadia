package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.TransactionDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.refanzzzz.tokonyadia.constant.Constant.TRANSACTION_DETAIL_API;

@RestController
@RequestMapping(TRANSACTION_DETAIL_API)
public class TransactionDetailController implements Controller<CommonResponse<TransactionDetailResponse>, TransactionDetailRequest> {
    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> getAll(String query, Integer page, Integer size, String sort) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> insert(TransactionDetailRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> remove(String id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> update(String id, TransactionDetailRequest request) {
        return null;
    }
}
