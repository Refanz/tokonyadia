package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.CustomerRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.CustomerResponse;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.refanzzzz.tokonyadia.constant.Constant.CUSTOMER_API;

@RestController
@RequestMapping(CUSTOMER_API)
@AllArgsConstructor
public class CustomerController implements Controller<CommonResponse<CustomerResponse>, CustomerRequest> {

    private CustomerService customerService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false) String sort) {


        CustomerRequest customerRequest = CustomerRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sortBy(sort)
                .build();

        Page<CustomerResponse> customerResponsePage = customerService.getAll(customerRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all customer", customerResponsePage);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> getById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get customer by id", customerResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> insert(@RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.insert(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "Successfuly create new customer", customerResponse);
    }

    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> remove(String id) {
        customerService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.NO_CONTENT, "Successfully delete customer with id: " + id, null);
    }

    @Override
    public ResponseEntity<CommonResponse<CustomerResponse>> update(String id, CustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully update customer with id: " + id, customerResponse);
    }
}
