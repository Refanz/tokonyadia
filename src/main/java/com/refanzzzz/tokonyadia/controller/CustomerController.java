package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.CustomerRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.CustomerResponse;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.refanzzzz.tokonyadia.constant.Constant.CUSTOMER_API;

@RestController
@RequestMapping(CUSTOMER_API)
@AllArgsConstructor
public class CustomerController implements Controller<CommonResponse<CustomerResponse>, CustomerRequest> {

    private CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sort) {


        CustomerRequest customerRequest = CustomerRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sortBy(sort)
                .build();

        Page<CustomerResponse> customerResponsePage = customerService.getAll(customerRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, Constant.SUCCESS_GET_ALL_CUSTOMER, customerResponsePage);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> getById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, Constant.SUCCESS_GET_CUSTOMER, customerResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> create(@RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.create(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_CUSTOMER, customerResponse);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> remove(@PathVariable String id) {
        customerService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.NO_CONTENT, Constant.SUCCESS_DELETE_CUSTOMER, null);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> update(@PathVariable String id, @RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_CUSTOMER, customerResponse);
    }
}
