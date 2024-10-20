package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionDetailResponse;
import com.refanzzzz.tokonyadia.service.TransactionDetailService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.refanzzzz.tokonyadia.constant.Constant.TRANSACTION_DETAIL_API;

@RestController
@RequestMapping(TRANSACTION_DETAIL_API)
@AllArgsConstructor
public class TransactionDetailController implements Controller<CommonResponse<TransactionDetailResponse>, TransactionDetailRequest> {

    private TransactionDetailService transactionDetailService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sort) {

        TransactionDetailRequest transactionDetailRequest = TransactionDetailRequest
                .builder()
                .page(page)
                .query(query)
                .size(size)
                .sortBy(sort)
                .build();

        Page<TransactionDetailResponse> transactionDetailResponsePage = transactionDetailService.getAll(transactionDetailRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all transaction detail", transactionDetailResponsePage);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> getById(@PathVariable String id) {
        TransactionDetailResponse transactionDetailResponse = transactionDetailService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get transaction detail", transactionDetailResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> create(@RequestBody TransactionDetailRequest request) {
        TransactionDetailResponse transactionDetailResponse = transactionDetailService.create(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "Successfully create transaction detail", transactionDetailResponse);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> remove(@PathVariable String id) {
        transactionDetailService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully delete transaction detail with id: " + id, null);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> update(@PathVariable String id, @RequestBody TransactionDetailRequest request) {
        TransactionDetailResponse transactionDetailResponse = transactionDetailService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully update transaction detail", transactionDetailResponse);
    }
}
